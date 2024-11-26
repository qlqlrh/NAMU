package com.green.namu.controller;

import com.green.namu.domain.OauthServerType;
import com.green.namu.service.OauthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/oauth")
@RestController
public class OauthController {

    private final OauthService oauthService;

    @SneakyThrows // checked exception을 숨기고, 자동으로 예외 처리를 해 줌
    @GetMapping("/{oauthServerType}")
    ResponseEntity<Void> redirectAuthCodeRequestUrl(
            @PathVariable("oauthServerType") OauthServerType oauthServerType,
            HttpServletResponse response
    ) {
        String redirectUrl = oauthService.getAuthCodeRequestUrl(oauthServerType);

        // 사용자가 브라우저 -> OAuth 서버로 리다이렉트 되어 인증 진행하도록 함
        response.sendRedirect(redirectUrl); // 여기서 발생 가능한 IOException 예외를 @SneakyThrows가 자동으로 처리
        return ResponseEntity.ok().build();
    }
}
