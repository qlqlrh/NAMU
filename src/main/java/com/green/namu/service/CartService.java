package com.green.namu.service;

import com.green.namu.domain.Cart;
import com.green.namu.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    public Optional<Cart> getCartById(Long cartId) {
        return cartRepository.findById(cartId);
    }

    public Cart saveCart(Cart cart) {
        return cartRepository.save(cart);
    }

    public Cart updateCart(Long cartId, Cart cartDetails) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
        cart.setMenu(cartDetails.getMenu());
        cart.setCartQuantity(cartDetails.getCartQuantity());
        cart.setStatus(cartDetails.getStatus());
        return cartRepository.save(cart);
    }

    public void deleteCart(Long cartId) {
        cartRepository.deleteById(cartId);
    }
}