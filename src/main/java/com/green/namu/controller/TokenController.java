package com.green.namu.controller;

import com.green.namu.common.exceptions.BaseException;
import com.green.namu.common.response.BaseResponse;
import com.green.namu.dto.PostLoginRes;
import com.green.namu.dto.UserIdReq;
import com.green.namu.service.AuthService;
import com.green.namu.utils.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static org.hibernate.query.sqm.tree.SqmNode.log;

@RestController
@RequestMapping("/api/token")
@RequiredArgsConstructor
@Tag(name = "토큰 발급", description = "토큰 발급 관련 API")
public class TokenController {

    private final JwtService jwtService;
    private final AuthService authService;

    @Operation(summary = "Access Token 및 Refresh Token 발급", description = "사용자의 ID를 기반으로 Access Token과 Refresh Token을 발급합니다.")
    @PostMapping("/issue")
    public BaseResponse<PostLoginRes> issueTokens(@RequestBody UserIdReq request) {
        try {
            Long userId = request.getUserId();

            // Access Token 및 Refresh Token 발급
            PostLoginRes tokens = authService.generateToken(userId);
            return new BaseResponse<>(tokens);
        } catch (BaseException e) {
            log.error("토큰 발급 중 오류 발생: ", e);
            return new BaseResponse<>(e.getStatus());
        }
    }

    @Operation(summary = "Access Token 재발급", description = "Refresh Token을 통해 Access Token을 재발급합니다.")
    @PostMapping("/refresh")
    public BaseResponse<String> refreshAccessToken(@RequestHeader("Authorization") String refreshToken) {
        try {
            // Refresh Token에서 AccessToken 재발급
            String newAccessToken = jwtService.refreshAccessToken(refreshToken);
            return new BaseResponse<>(newAccessToken);
        } catch (BaseException e) {
            log.error("Access Token 토큰 재발급 중 오류 발생: ", e);
            return new BaseResponse<>(e.getStatus());
        }
    }
}