package com.ilearn.book_buddy.service.interfaces;

import com.ilearn.book_buddy.data.entity.RefreshToken;
import com.ilearn.book_buddy.rest.request.RefreshTokenRequest;
import com.ilearn.book_buddy.rest.response.RefreshTokenResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseCookie;

public interface RefreshTokenUseCase {
    RefreshToken createRefreshToken(Long  userId);
    RefreshToken verifyExpiration(RefreshToken token);
    RefreshTokenResponse generateNewToken(RefreshTokenRequest request);
    ResponseCookie generateRefreshTokenCookie(String token);
    String getRefreshTokenFromCookies(HttpServletRequest request);
    void deleteByToken(String token);
    ResponseCookie getCleanRefreshTokenCookie();
}
