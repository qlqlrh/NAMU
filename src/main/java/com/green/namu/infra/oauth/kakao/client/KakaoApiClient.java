package com.green.namu.infra.oauth.kakao.client;

import com.green.namu.infra.oauth.kakao.dto.KakaoMemberResponse;
import com.green.namu.infra.oauth.kakao.dto.KakaoToken;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;

// Http Interface Client 사용 (스프링6에서 새로 추가된 기능)
public interface KakaoApiClient {

    @PostExchange(url = "https://kauth.kakao.com/oauth/token", contentType = APPLICATION_FORM_URLENCODED_VALUE)
    KakaoToken fetchToken(@RequestBody MultiValueMap<String, String> params);


    // AccessToken을 통해 사용자 정보를 받아오기
    @GetExchange("https://kapi.kakao.com/v2/user/me")
    KakaoMemberResponse fetchMember(@RequestHeader(name = AUTHORIZATION) String bearerToken);
}
