package com.green.namu.service;

import com.green.namu.common.exceptions.BaseException;
import com.green.namu.common.response.BaseResponseStatus;
import com.green.namu.domain.OauthMember;
import com.green.namu.domain.OauthServerType;
import com.green.namu.domain.User;
import com.green.namu.domain.authcode.AuthCodeRequestUrlProviderComposite;
import com.green.namu.domain.client.OauthMemberClientComposite;
import com.green.namu.dto.PostLoginRes;
import com.green.namu.repository.OauthMemberRepository;
import com.green.namu.repository.UserRepository;
import com.green.namu.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OauthService {

    private final AuthCodeRequestUrlProviderComposite authCodeRequestUrlProviderComposite;
    private final OauthMemberClientComposite oauthMemberClientComposite;
    private final OauthMemberRepository oauthMemberRepository;
    private final UserRepository userRepository;
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
                    // OauthMember 저장 및 반환
                    return oauthMemberRepository.save(oauthMember);
                });

        // 3. User 저장 또는 업데이트
        User user = userService.findOrCreateUserFromOauth(savedOauthMember);

        // 4. Access Token 및 Refresh Token 생성
        String accessToken = jwtService.createAccessToken(user.getUserId());
        String refreshToken = jwtService.createRefreshToken(user.getUserId());

        // 5. Refresh Token 저장 (User 업데이트)
        userService.updateRefreshToken(user.getUserId(), refreshToken);

        // 6. PostLoginRes 반환
        return new PostLoginRes(accessToken, refreshToken);
    }

}