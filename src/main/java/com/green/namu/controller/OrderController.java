package com.green.namu.controller;

import com.green.namu.common.exceptions.BaseException;
import com.green.namu.common.response.BaseResponse;
import com.green.namu.common.response.BaseResponseStatus;
import com.green.namu.dto.OrderRequest;
import com.green.namu.dto.OrderResponse;
import com.green.namu.service.OrderService;
import com.green.namu.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static org.hibernate.query.sqm.tree.SqmNode.log;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final JwtService jwtService;

    @PostMapping("/{userId}")
    public BaseResponse<OrderResponse> createOrder(
            @PathVariable("userId") Long userId,
            @RequestBody OrderRequest request,
            @RequestHeader("Authorization") String token) {

        // JWT 검증 및 사용자 ID 확인
        if (!jwtService.validateTokenAndUser(token, true, userId)) {
            throw new BaseException(BaseResponseStatus.AUTHENTICATION_FAILED);
        }

        try {
            OrderResponse response = orderService.createOrder(userId, request);
            return new BaseResponse<>(response);
        } catch (BaseException e) {
            log.error("주문 처리 중 오류 발생: e");
            return new BaseResponse<>(e.getStatus());
        }
    }
}
