package com.green.namu.domain.authcode;

import com.green.namu.domain.OauthServerType;

public interface AuthCodeRequestUrlProvider {

    // 자신이 어떤 OauthServerType을 지원할 수 있는지 나타냄
    // 예를 들어 KakaoAuthCodeRequestUrlProvider는 OauthServerType으로 KAKAO 반환
    OauthServerType supportServer();

    // URL을 생성해서 반환 -> 해당 주소로 Redirect 하면 로그인 화면이 나옴
    String provide();
}
