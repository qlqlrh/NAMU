package com.green.namu.controller;

import com.green.namu.common.exceptions.BaseException;
import com.green.namu.common.response.BaseResponse;
import com.green.namu.utils.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/token")
@RequiredArgsConstructor
@Tag(name = "토큰 발급", description = "토큰 발급 관련 API")
public class TokenController {

    private final JwtService jwtService;

    @Operation(summary = "Access Token 재발급", description = "Refresh Token을 통해 Access Token을 재발급합니다.")
    @PostMapping("/refresh")
    public BaseResponse<String> refreshAccessToken(@RequestHeader("Authorization") String refreshToken) {
        try {
            // Refresh Token에서 AccessToken 재발급
            String newAccessToken = jwtService.refreshAccessToken(refreshToken);
            return new BaseResponse<>(newAccessToken);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
}
