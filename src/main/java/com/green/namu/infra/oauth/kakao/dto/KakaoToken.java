package com.green.namu.infra.oauth.kakao.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class) // 응답을 token_type -> tokenType으로 변환해서 받을 수 있음
public record KakaoToken(
        String tokenType, // Bearer로 고정
        String accessToken,
        String idToken,
        String expiresIn, // 액세스 토큰 만료 시간(초)
        String refreshToken,
        String refreshTokenExpiresIn, // 리프레시 토큰 만료 시간(초)
        String scope
) {
}
