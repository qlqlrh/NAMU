package com.green.namu.utils;

import com.green.namu.common.exceptions.BaseException;
import com.green.namu.common.response.BaseResponseStatus;
import com.green.namu.service.TokenBlacklistService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Date;

import static com.green.namu.common.response.BaseResponseStatus.EMPTY_JWT;
import static com.green.namu.common.response.BaseResponseStatus.INVALID_JWT;

@Service
public class JwtService {

    @Value("${custom.jwt.secretKey}")
    private String JWT_SECRET_KEY;

    @Value("${custom.jwt.refreshSecretKey}")
    private String REFRESH_SECRET_KEY;

    @Autowired
    private
    TokenBlacklistService tokenBlacklistService;

    // Access Token 유효기간: 15분
    private static final long ACCESS_TOKEN_EXPIRATION = 1000 * 60 * 15;

    // Refresh Token 유효기간: 7일
    private static final long REFRESH_TOKEN_EXPIRATION = 1000 * 60 * 60 * 24 * 7;

    /**
     * Access Token 생성
     *
     * @param userId 사용자 아이디
     * @return Access Token 문자열
     */
    public String createAccessToken(Long userId) {
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam("type", "jwt")
                .claim("userId", userId)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET_KEY)
                .compact();
    }

    /**
     * Refresh Token 생성
     *
     * @param userId 사용자 아이디
     * @return Refresh Token 문자열
     */
    public String createRefreshToken(Long userId) {
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam("type", "jwt")
                .claim("userId", userId)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, REFRESH_SECRET_KEY)
                .compact();
    }

    /**
     * Refresh Token으로 Access Token 재발급
     *
     * @param refreshToken Refresh Token
     * @return 새로운 Access Token
     */
    public String refreshAccessToken(String refreshToken) {
        // Refresh Token 검증 (블랙리스트 및 만료 여부 포함)
        if (!validateToken(refreshToken, false)) {
            throw new BaseException(BaseResponseStatus.INVALID_REFRESH_TOKEN);
        }

        // Refresh Token에서 userId 추출
        Long userId = getUserId(refreshToken, false);

        // 새로운 AccessToken 생성
        return createAccessToken(userId);
    }

    /**
     * Header의 Authorization에서 JWT 추출
     *
     * @return JWT 문자열
     */
    public String getJwt() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String token = request.getHeader("Authorization");
        if (token == null || token.trim().isEmpty()) {
            throw new BaseException(EMPTY_JWT);
        }
        return token;
    }

    /**
     * JWT에서 사용자 아이디 추출
     *
     * @param token         JWT 문자열
     * @param isAccessToken Access Token 여부 (true이면 Access Token, false이면 Refresh Token)
     * @return 사용자 아이디
     * @throws BaseException 토큰 파싱 실패시 예외 발생
     */
    public Long getUserId(String token, boolean isAccessToken) throws BaseException {
        // JWT 파싱 및 검증
        Jws<Claims> claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(isAccessToken ? JWT_SECRET_KEY : REFRESH_SECRET_KEY)
                    .parseClaimsJws(token);
        } catch (Exception ignored) {
            throw new BaseException(INVALID_JWT);
        }

        // userId 추출
        return claims.getBody().get("userId", Long.class);
    }

    /**
     * JWT 유효성 검증 (블랙리스트 및 만료 여부 포함)
     *
     * @param token         JWT 문자열
     * @param isAccessToken Access Token 여부
     * @return 유효하면 true, 그렇지 않으면 false
     */
    public boolean validateToken(String token, boolean isAccessToken) {
        // 토큰이 블랙리스트에 있는지 확인
        if (tokenBlacklistService.isTokenBlacklisted(token)) {
            return false;
        }
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(isAccessToken ? JWT_SECRET_KEY : REFRESH_SECRET_KEY)
                    .parseClaimsJws(token);

            // 만료 여부 확인
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false; // 유효하지 않은 토큰
        }
    }

    /**
     * JWT와 사용자 아이디의 유효성 검증 (유효하지 않으면 예외 발생)
     *
     * @param token         JWT 문자열
     * @param isAccessToken Access Token 여부
     * @return 검증된 사용자 아이디
     */
    public Long getUserIdOrThrow(String token, boolean isAccessToken) {
        // JWT 유효성 검증
        if (!validateToken(token, isAccessToken)) {
            throw new BaseException(BaseResponseStatus.AUTHENTICATION_FAILED);
        }

        // JWT에서 사용자 ID 추출
        try {
            return getUserId(token, isAccessToken);
        } catch (BaseException e) {
            throw new BaseException(BaseResponseStatus.AUTHENTICATION_FAILED);
        }
    }

    /**
     * 토큰 무효화 (블랙리스트에 등록)
     * @param token 무효화할 JWT 문자열
     */
    public void invalidateToken(String token) {
        tokenBlacklistService.blacklistToken(token);
    }

}
