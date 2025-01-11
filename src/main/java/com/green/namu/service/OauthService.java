package com.green.namu.service;

import com.green.namu.domain.OauthMember;
import com.green.namu.domain.OauthServerType;
import com.green.namu.domain.authcode.AuthCodeRequestUrlProviderComposite;
import com.green.namu.domain.client.OauthMemberClientComposite;
import com.green.namu.dto.PostLoginRes;
import com.green.namu.repository.OauthMemberRepository;
import com.green.namu.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OauthService {

    private final AuthCodeRequestUrlProviderComposite authCodeRequestUrlProviderComposite;
    private final OauthMemberClientComposite oauthMemberClientComposite;
    private final OauthMemberRepository oauthMemberRepository;
    private final JwtService jwtService;
    private final UserService userService;

    // 실제 URL을 만들어내는 매서드
    public String getAuthCodeRequestUrl(OauthServerType oauthServerType) {
        return authCodeRequestUrlProviderComposite.provide(oauthServerType);
    }

    // 토큰을 통해 받아온 사용자 정보를 바탕으로 로그인/회원가입
    public PostLoginRes login(OauthServerType oauthServerType, String authCode) {
        // 1. OAuth 서버에서 사용자 정보 가져오기
        OauthMember oauthMember = oauthMemberClientComposite.fetch(oauthServerType, authCode);

        // 2. 사용자 등록 (회원가입이 되어 있지 않다면 회원가입 진행)
        OauthMember savedOauthMember = oauthMemberRepository.findByOauthId(oauthMember.getOauthId())
                .orElseGet(() -> {
                    // OauthMember 저장
                    OauthMember newOauthMember = oauthMemberRepository.save(oauthMember);
                    // OauthMember를 기반으로 User 생성 및 저장
                    userService.saveUserFromOauth(newOauthMember);
                    return newOauthMember; // 저장된 OauthMember 반환
                });

        Long userId = savedOauthMember.getId();

        // 3. Access Token 및 Refresh Token 생성
        String accessToken = jwtService.createAccessToken(userId);
        String refreshToken = jwtService.createRefreshToken(userId);

        // 4. Refresh Token 저장 (선택적으로 구현)
//    tokenStorageService.storeRefreshToken(userId, refreshToken);

        // 5. PostLoginRes 반환
        return new PostLoginRes(accessToken, refreshToken);
    }

}