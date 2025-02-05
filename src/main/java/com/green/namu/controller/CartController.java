package com.green.namu.controller;

import com.green.namu.common.exceptions.BaseException;
import com.green.namu.common.response.ApiResponse;
import com.green.namu.common.response.BaseResponseStatus;
import com.green.namu.domain.Cart;
import com.green.namu.dto.CartDto;
import com.green.namu.service.CartService;
import com.green.namu.utils.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@Tag(name = "장바구니", description = "장바구니 관련 API")
public class CartController {
    @Autowired
    private CartService cartService;

    @Autowired
    private JwtService jwtService;

    // 장바구니에 상품 추가
    @Operation(summary = "장바구니에 상품 추가")
    @PostMapping
    public ResponseEntity<?> addCart(@RequestBody CartDto cartDto, @RequestHeader("Authorization") String token) {
        try {
            // "Bearer " 접두어 제거
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            Long userId = jwtService.getUserIdOrThrow(token, true);
            Cart newCart = cartService.saveCart(cartDto, userId);
            return ResponseEntity.ok(new ApiResponse(BaseResponseStatus.CART_ADDED_SUCCESS, newCart));
        } catch (BaseException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(e.getStatus(), null));
        }
    }

    // 특정 유저의 장바구니 목록 조회
    @Operation(summary = "특정 유저의 장바구니 목록 조회")
    @GetMapping
    public ResponseEntity<?> getCartsByUser(@RequestHeader("Authorization") String token) {
        try {
            // "Bearer " 접두어 제거
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            Long userId = jwtService.getUserIdOrThrow(token, true);
            List<Cart> carts = cartService.getCartsByUserId(userId);
            if (carts.isEmpty()) {
                return ResponseEntity.ok(new ApiResponse(BaseResponseStatus.CART_EMPTY, null));
            }
            return ResponseEntity.ok(new ApiResponse(BaseResponseStatus.SUCCESS, carts));
        } catch (BaseException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(e.getStatus(), null));
        }
    }

    // 장바구니 상품 정보 수정
    @Operation(summary = "장바구니 상품 정보 수정")
    @PutMapping("/{cartId}")
    public ResponseEntity<?> updateCart(@PathVariable Long cartId, @RequestBody CartDto cartDto, @RequestHeader("Authorization") String token) {
        try {
            // "Bearer " 접두어 제거
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            Long userId = jwtService.getUserIdOrThrow(token, true);
            Cart updatedCart = cartService.updateCart(cartId, cartDto, userId);
            return ResponseEntity.ok(new ApiResponse(BaseResponseStatus.SUCCESS, updatedCart));
        } catch (BaseException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(e.getStatus(), null));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ApiResponse(BaseResponseStatus.CART_ITEM_NOT_FOUND, null));
        }
    }

    // 장바구니에서 상품 삭제
    @Operation(summary = "장바구니에서 상품 삭제")
    @DeleteMapping("/{cartId}")
    public ResponseEntity<?> deleteCart(@PathVariable Long cartId, @RequestHeader("Authorization") String token) {
        try {
            // "Bearer " 접두어 제거
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            Long userId = jwtService.getUserIdOrThrow(token, true);
            cartService.deleteCart(cartId, userId);
            return ResponseEntity.ok(new ApiResponse(BaseResponseStatus.SUCCESS, null));
        } catch (BaseException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(e.getStatus(), null));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ApiResponse(BaseResponseStatus.CART_ITEM_NOT_FOUND, null));
        }
    }
}
