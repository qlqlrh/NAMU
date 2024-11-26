package com.green.namu.domain;

import java.util.Locale;

// 카카오, 구글, 네이버 등 Oauth2.0 인증을 제공하는 서버의 종류를 명시할 Enum
public enum OauthServerType {
    KAKAO,
    ;

    public static OauthServerType fromName(String type) {
        return OauthServerType.valueOf(type.toUpperCase(Locale.ENGLISH));
    }
}
