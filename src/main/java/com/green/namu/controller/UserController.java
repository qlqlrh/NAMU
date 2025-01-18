package com.green.namu.controller;

import com.green.namu.common.exceptions.BaseException;
import com.green.namu.common.response.BaseResponse;
import com.green.namu.common.response.BaseResponseStatus;
import com.green.namu.dto.MyPageRes;
import com.green.namu.service.UserService;
import com.green.namu.utils.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import static org.hibernate.query.sqm.tree.SqmNode.log;

@RestController
@RequiredArgsConstructor
@Tag(name = "사용자 컨트롤러", description = "사용자 관련 API")
public class UserController {
    private final UserService userService;
    private final JwtService jwtService;

    @Operation(summary = "마이페이지 조회", description = "사용자의 마이페이지 정보를 조회합니다.")
    @GetMapping("/mypage/{userId}")
    public BaseResponse<MyPageRes> getMypage(
            @PathVariable("userId") Long userId,
            @RequestHeader("Authorization") String token) {

        try {
            // JWT 검증 및 사용자 ID 추출
            Long tokenUserId = jwtService.getUserIdOrThrow(token, true);

            // 요청한 userId와 JWT userId 비교
            if (!userId.equals(tokenUserId)) {
                throw new BaseException(BaseResponseStatus.FORBIDDEN_ACCESS_MYPAGE);
            }

            MyPageRes response = userService.getMyPage(userId);
            return new BaseResponse<>(response);

        } catch (BaseException e) {
            log.error("마이페이지 조회 중 오류 발생: ", e);
            return new BaseResponse<>(e.getStatus());
        }
    }

}
