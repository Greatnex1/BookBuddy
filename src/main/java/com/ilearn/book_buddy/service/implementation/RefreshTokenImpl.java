package com.ilearn.book_buddy.service.implementation;

import com.ilearn.book_buddy.data.entity.ApplicationUserEntity;
import com.ilearn.book_buddy.data.entity.RefreshToken;
import com.ilearn.book_buddy.data.enums.TokenType;
import com.ilearn.book_buddy.handlers.exception.TokenException;
import com.ilearn.book_buddy.repository.ApplicationUserRepository;
import com.ilearn.book_buddy.repository.RefreshTokenRepository;
import com.ilearn.book_buddy.rest.request.RefreshTokenRequest;
import com.ilearn.book_buddy.rest.response.RefreshTokenResponse;
import com.ilearn.book_buddy.service.interfaces.JwtUseCase;
import com.ilearn.book_buddy.service.interfaces.RefreshTokenUseCase;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import java.time.Instant;
import java.util.Base64;
import java.util.UUID;
@RequiredArgsConstructor
@Service
@Slf4j
public class RefreshTokenImpl implements RefreshTokenUseCase {

    private final ApplicationUserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUseCase jwtService;

    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;
    @Value("${application.security.jwt.refresh-token.cookie-name}")
    private String refreshTokenName;

    @Override
    public RefreshToken createRefreshToken(Long userId) {
        ApplicationUserEntity user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Not Found"));
        RefreshToken refreshToken = RefreshToken.builder()
                .revoked(false)
                .user(user)
                .token(Base64.getEncoder().encodeToString(UUID.randomUUID().toString().getBytes()))
                .expiryDate(Instant.now().plusMillis(refreshExpiration))
                .build();
        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token == null) {
            log.error("Token is null");
            throw new TokenException(null, "Token is null");
        }
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new TokenException(token.getToken(), "Refresh token was expired. Please make a new authentication request");
        }
        return token;
    }


    @Override
    public RefreshTokenResponse generateNewToken(RefreshTokenRequest request) {
        ApplicationUserEntity user = refreshTokenRepository.findByToken(request.getRefreshToken())
                .map(this::verifyExpiration)
                .map(RefreshToken::getUser)
                .orElseThrow(() -> new TokenException(request.getRefreshToken(), "Refresh token does not exist"));

        String token = jwtService.generateToken(user);
        return RefreshTokenResponse.builder()
                .accessToken(token)
                .refreshToken(request.getRefreshToken())
                .tokenType(TokenType.BEARER.name())
                .build();
    }

    @Override
    public ResponseCookie generateRefreshTokenCookie(String token) {
        return ResponseCookie.from(refreshTokenName, token)
                .path("/")
                .maxAge(refreshExpiration / 1000) // 15 days in seconds
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .build();
    }

    @Override
    public String getRefreshTokenFromCookies(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, refreshTokenName);
        if (cookie != null) {
            return cookie.getValue();
        } else {
            return "";
        }
    }

    @Override
    public void deleteByToken(String token) {
        refreshTokenRepository.findByToken(token).ifPresent(refreshTokenRepository::delete);
    }

    @Override
    public ResponseCookie getCleanRefreshTokenCookie() {
        return ResponseCookie.from(refreshTokenName, "")
                .path("/")
                .build();
    }
}