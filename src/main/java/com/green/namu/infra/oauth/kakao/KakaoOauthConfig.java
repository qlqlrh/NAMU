package com.green.namu.infra.oauth.kakao;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "oauth.kakao")
public record KakaoOauthConfig (
    String redirectUri,
    String clientId,
    String clientSecret,
    String[] scope
    ) {
}
