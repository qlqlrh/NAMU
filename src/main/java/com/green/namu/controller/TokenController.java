package com.green.namu.controller;

import com.green.namu.common.exceptions.BaseException;
import com.green.namu.common.response.BaseResponse;
import com.green.namu.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/token")
@RequiredArgsConstructor
public class TokenController {

    private final JwtService jwtService;

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
