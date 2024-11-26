package com.green.namu.domain.authcode;

import com.green.namu.domain.OauthServerType;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

@Component
public class AuthCodeRequestUrlProviderComposite {

    // OauthServerType 종류에 따라 해당하는 AuthCodeRequestUrlProvider를 사용하여 URL 생성
    // 다른 소셜 로그인을 추가할 때, 기존 코드 변경 없이 새로 추가된 타입 사용 가능

    private final Map<OauthServerType, AuthCodeRequestUrlProvider> mapping;

    public AuthCodeRequestUrlProviderComposite(Set<AuthCodeRequestUrlProvider> providers) {
        mapping = providers.stream()
                .collect(toMap(
                        AuthCodeRequestUrlProvider::supportServer,
                        Function.identity()
                ));
    }

    public AuthCodeRequestUrlProvider getProvider(OauthServerType oauthServerType) {
        return Optional.ofNullable(mapping.get(oauthServerType))
                .orElseThrow(() -> new RuntimeException("지원하지 않는 소셜 로그인 타입입니다."));
    }

    public String provide(OauthServerType oauthServerType) {
        return getProvider(oauthServerType).provide();
    }
}
