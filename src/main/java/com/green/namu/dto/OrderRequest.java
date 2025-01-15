package com.green.namu.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest { // 장바구니 화면에서 주문 버튼 눌렀을 때 요청오는 데이터
    @JsonProperty("store_id")
    private Long storeId;

    private List<OrderMenuRequest> menus;

    @JsonProperty("payment_method")
    private String paymentMethod;

    @JsonProperty("total_price")
    private int totalPrice;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderMenuRequest {
        @JsonProperty("menu_id")
        private Long menuId;

        @JsonProperty("cart_quantity")
        private int cartQuantity;
    }
}
