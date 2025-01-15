package com.green.namu.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.green.namu.domain.status.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {  // 주문서에 보일 내용

    @JsonProperty("order_id")
    private Long orderId;

    private String status; // UNORDERED, ORDERED, PICKED_UP

    @JsonProperty("set_name")
    private String setName;

    @JsonProperty("total_price")
    private int totalPrice;

    @JsonProperty("store_name")
    private String storeName;

    @JsonProperty("store_address")
    private String storeAddress;

    @JsonProperty("store_phone")
    private String storePhone;

    @JsonProperty("pickup_time")
    private String pickupTime;
}
