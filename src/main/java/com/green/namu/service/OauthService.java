package com.green.namu.service;

import com.green.namu.domain.OauthMember;
import com.green.namu.domain.OauthServerType;
import com.green.namu.domain.authcode.AuthCodeRequestUrlProviderComposite;
import com.green.namu.domain.client.OauthMemberClientComposite;
import com.green.namu.repository.OauthMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OauthService {

    private final AuthCodeRequestUrlProviderComposite authCodeRequestUrlProviderComposite;
    private final OauthMemberClientComposite oauthMemberClientComposite;
    private final OauthMemberRepository oauthMemberRepository;

    // 실제 URL을 만들어내는 매서드
    public String getAuthCodeRequestUrl(OauthServerType oauthServerType) {
        return authCodeRequestUrlProviderComposite.provide(oauthServerType);
    }

    // 토큰을 통해 받아온 사용자 정보를 바탕으로 로그인/회원가입
    public Long login(OauthServerType oauthServerType, String authCode) {
        OauthMember oauthMember = oauthMemberClientComposite.fetch(oauthServerType, authCode);

        // 회원가입이 되어있지 않다면 회원가입 진행 (OauthId는 unique 보장되어 있음)
        OauthMember saved = oauthMemberRepository.findByOauthId(oauthMember.getOauthId())
                .orElseGet(() -> oauthMemberRepository.save(oauthMember));
        return saved.getId(); // 원래 JWT를 사용한다면 여기서 JWT로 AccessToken을 생성해서 반환해야 함
    }
}
