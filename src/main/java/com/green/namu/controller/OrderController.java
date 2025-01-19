package com.green.namu.controller;

import com.green.namu.common.exceptions.BaseException;
import com.green.namu.common.response.BaseResponse;
import com.green.namu.common.response.BaseResponseStatus;
import com.green.namu.dto.OrderReq;
import com.green.namu.dto.OrderRes;
import com.green.namu.service.OrderService;
import com.green.namu.utils.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static org.hibernate.query.sqm.tree.SqmNode.log;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
@Tag(name = "주문", description = "주문 관련 API")
public class OrderController {
    private final OrderService orderService;
    private final JwtService jwtService;

    @Operation(summary = "주문 요청", description = "장바구니 화면에서 주문을 요청하면, 주문을 처리하고 관련 필드 값들을 업데이트합니다.")
    @PostMapping("/{userId}")
    public BaseResponse<OrderRes> createOrder(
            @PathVariable("userId") Long userId,
            @RequestBody OrderReq request,
            @RequestHeader("Authorization") String token) {

        try {
            // JWT 검증 및 사용자 ID 추출
            Long tokenUserId = jwtService.getUserIdOrThrow(token, true);

            // 요청한 userId와 JWT userId 비교
            if (!userId.equals(tokenUserId)) {
                throw new BaseException(BaseResponseStatus.FORBIDDEN_ACCESS_MYPAGE);
            }

            OrderRes response = orderService.createOrder(userId, request);
            return new BaseResponse<>(response);
        } catch (BaseException e) {
            log.error("주문 처리 중 오류 발생: ", e);
            return new BaseResponse<>(e.getStatus());
        }
    }
}
