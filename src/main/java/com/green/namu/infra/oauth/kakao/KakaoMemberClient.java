package com.green.namu.infra.oauth.kakao;

import com.green.namu.domain.OauthMember;
import com.green.namu.domain.OauthServerType;
import com.green.namu.domain.client.OauthMemberClient;
import com.green.namu.infra.oauth.kakao.client.KakaoApiClient;
import com.green.namu.infra.oauth.kakao.dto.KakaoMemberResponse;
import com.green.namu.infra.oauth.kakao.dto.KakaoToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

@Component
@RequiredArgsConstructor
@Slf4j // 로그 객체 자동 생성
public class KakaoMemberClient implements OauthMemberClient {

    private final KakaoApiClient kakaoApiClient;
    private final KakaoOauthConfig kakaoOauthConfig;


    @Override
    public OauthServerType supportServer() {
        return OauthServerType.KAKAO;
    }

    private MultiValueMap<String, String> tokenRequestParams(String authCode) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", kakaoOauthConfig.clientId());
        params.add("redirect_uri", kakaoOauthConfig.redirectUri());
        params.add("code", authCode);
        params.add("client_secret", kakaoOauthConfig.clientSecret());
        return params;
    }

    /*
        (1) AuthCode를 통해 AccessToken 조회
        (2) AccessToken을 통해 회원 정보 받아옴
        (3) 회원 정보를 OauthMember 객체로 변환
     */
    @Override
    public OauthMember fetch(String authCode) {
        try {
            KakaoToken tokenInfo = kakaoApiClient.fetchToken(tokenRequestParams(authCode)); // (1)
            KakaoMemberResponse kakaoMemberResponse =
                    kakaoApiClient.fetchMember("Bearer " + tokenInfo.accessToken()); // (2)
            return kakaoMemberResponse.toDomain(); // (3)
        } catch (HttpClientErrorException e) {
            log.error("HTTP Client Error: {}", e.getMessage());
            throw new RuntimeException("카카오 토큰 발급 실패");
        } catch (HttpServerErrorException e) {
            log.error("HTTP Server Error: {}", e.getMessage());
            throw new RuntimeException("카카오 서버 오류");
        }

    }
}
