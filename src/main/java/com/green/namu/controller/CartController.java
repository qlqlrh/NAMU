package com.green.namu.controller;

import com.green.namu.common.response.ApiResponse;
import com.green.namu.common.response.BaseResponseStatus;
import com.green.namu.domain.Cart;
import com.green.namu.service.CartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    // 장바구니 목록 조회
    @GetMapping
    public ResponseEntity<?> getAllCarts() {
        List<Cart> carts = cartService.getAllCarts();
        if (carts.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse(BaseResponseStatus.CART_EMPTY, null));
        }
        return ResponseEntity.ok(new ApiResponse(BaseResponseStatus.SUCCESS, carts));
    }

    // 장바구니에 상품 추가
    @PostMapping
    public ResponseEntity<?> addCart(@RequestBody Cart cart) {
        Cart newCart = cartService.saveCart(cart);
        return ResponseEntity.ok(new ApiResponse(BaseResponseStatus.CART_ADDED_SUCCESS, newCart));
    }

    // 장바구니 상품 정보 수정
    @PutMapping("/{cartId}")
    public ResponseEntity<?> updateCart(@PathVariable Long cartId, @RequestBody Cart cartDetails) {
        try {
            Cart updatedCart = cartService.updateCart(cartId, cartDetails);
            return ResponseEntity.ok(new ApiResponse(BaseResponseStatus.SUCCESS, updatedCart));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ApiResponse(BaseResponseStatus.CART_ITEM_NOT_FOUND, null));
        }
    }

    // 장바구니에서 상품 삭제
    @DeleteMapping("/{cartId}")
    public ResponseEntity<?> deleteCart(@PathVariable Long cartId) {
        try {
            cartService.deleteCart(cartId);
            return ResponseEntity.ok(new ApiResponse(BaseResponseStatus.SUCCESS, null));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ApiResponse(BaseResponseStatus.CART_ITEM_NOT_FOUND, null));
        }
    }
}
