package com.green.namu.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class OauthId {

    /*
        다른 서비스 간 식별자 중복 방지를 위한 클래스
     */

    // 특정 인증 서버의 식별자 값
    @Column(nullable = false, name = "oauth_server_id")
    private String oauthServerId;

    // 식별자 값을 제공하는 서비스 타입
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "oauth_server")
    private OauthServerType oauthServerType;

}
