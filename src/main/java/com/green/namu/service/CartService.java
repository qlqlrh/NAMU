package com.green.namu.service;

import com.green.namu.common.exceptions.BaseException;
import com.green.namu.common.response.BaseResponseStatus;
import com.green.namu.domain.Cart;
import com.green.namu.domain.Menu;
import com.green.namu.domain.User;
import com.green.namu.dto.CartDto;
import com.green.namu.repository.CartRepository;
import com.green.namu.repository.MenuRepository;
import com.green.namu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MenuRepository menuRepository;

    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    public List<Cart> getCartsByUserId(Long userId) {
        return cartRepository.findByUserUserId(userId);
    }

    public Optional<Cart> getCartById(Long cartId) {
        return cartRepository.findById(cartId);
    }

    public Cart saveCart(CartDto cartDto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_USER_ID));
        Menu menu = menuRepository.findById(cartDto.getMenuId())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_MENU_ID));
        Cart cart = Cart.builder()
                .cartQuantity(cartDto.getCartQuantity())
                .request(cartDto.getRequest())
                .status(cartDto.getStatus())
                .user(user)
                .menu(menu)
                .build();
        return cartRepository.save(cart);
    }

    public Cart updateCart(Long cartId, CartDto cartDto, Long userId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        // 유저 ID 검증
        if (!cart.getUser().getUserId().equals(userId)) {
            throw new BaseException(BaseResponseStatus.FORBIDDEN_ACCESS);
        }

        // 변경 가능한 필드 업데이트
        cart.setCartQuantity(cartDto.getCartQuantity());
        cart.setRequest(cartDto.getRequest());
        cart.setStatus(cartDto.getStatus());
        // 필요한 경우 메뉴 변경 등 추가 사항 반영 가능

        return cartRepository.save(cart);
    }

    public void deleteCart(Long cartId, Long userId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        // 유저 ID 검증
        if (!cart.getUser().getUserId().equals(userId)) {
            throw new BaseException(BaseResponseStatus.FORBIDDEN_ACCESS);
        }

        cartRepository.deleteById(cartId);
    }
}