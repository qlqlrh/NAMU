package com.green.namu.infra.oauth.kakao;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "oauth.kakao") // .properties 파일 보고 생성
public record KakaoOauthConfig (
    String redirectUri,
    String clientId,
    String clientSecret,
    String[] scope
    ) {
}
