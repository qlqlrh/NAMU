package com.green.namu.utils;

import com.green.namu.common.exceptions.BaseException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
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

    // Access Token 유효기간: 15분
    private static final long ACCESS_TOKEN_EXPIRATION = 1000 * 60 * 15;

    // Refresh Token 유효기간: 7일
    private static final long REFRESH_TOKEN_EXPIRATION = 1000 * 60 * 60 * 24 * 7;

    /*
    Access Token 생성
    @param userId
    @return String
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

    /*
    Refresh Token 생성
    @param userId
    @return String
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

    /*
    Header에서 Authorization 으로 JWT 추출
    @return String
     */
    public String getJwt() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return request.getHeader("Authorization");
    }

    /*
    JWT에서 userId 추출
    @return Long
    @throws BaseException
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
}
