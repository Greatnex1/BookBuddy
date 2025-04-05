package com.ilearn.book_buddy.service.implementation;

import com.ilearn.book_buddy.data.entity.ApplicationUserEntity;
import com.ilearn.book_buddy.data.enums.TokenType;
import com.ilearn.book_buddy.handlers.exception.GenericException;
import com.ilearn.book_buddy.repository.ApplicationUserRepository;
import com.ilearn.book_buddy.rest.request.AuthenticationRequest;
import com.ilearn.book_buddy.rest.request.RegisterRequest;
import com.ilearn.book_buddy.rest.response.AuthenticationResponse;
import com.ilearn.book_buddy.service.interfaces.AuthUseCase;
import com.ilearn.book_buddy.service.interfaces.JwtUseCase;
import com.ilearn.book_buddy.service.interfaces.RefreshTokenUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.ilearn.book_buddy.constants.ErrorMessages.USER_ALREADY_EXIST;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService implements AuthUseCase {
    private final PasswordEncoder passwordEncoder;
    private final JwtUseCase jwtService;
    private final ApplicationUserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenUseCase refreshTokenService;

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new GenericException(USER_ALREADY_EXIST, HttpStatus.BAD_REQUEST);
        }
        ApplicationUserEntity user = ApplicationUserEntity.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        user = userRepository.save(user);
        String jwt = jwtService.generateToken(user);
        var refreshToken = refreshTokenService.createRefreshToken(user.getId());

        var roles = user.getRole().getAuthorities()
                .stream()
                .map(SimpleGrantedAuthority::getAuthority)
                .toList();
        log.info("Login Details: username: {}, password: {} ",request.getEmail(),request.getPassword());
        return AuthenticationResponse.builder()
                .accessToken(jwt)
                .email(user.getEmail())
                .id(user.getId())
                .refreshToken(refreshToken.getToken())
                .roles(roles)
                .tokenType( TokenType.BEARER.name())
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));

        var user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
        var roles = user.getRole().getAuthorities()
                .stream()
                .map(SimpleGrantedAuthority::getAuthority)
                .toList();
        var jwt = jwtService.generateToken(user);
        var refreshToken = refreshTokenService.createRefreshToken(user.getId());
        return AuthenticationResponse.builder()
                .accessToken(jwt)
                .roles(roles)
                .email(user.getEmail())
                .id(user.getId())
                .refreshToken(refreshToken.getToken())
                .tokenType( TokenType.BEARER.name())
                .build();
    }}
