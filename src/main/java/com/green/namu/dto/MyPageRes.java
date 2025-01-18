package com.green.namu.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MyPageRes {
    @JsonProperty("profile_url")
    private String profileUrl;

    @JsonProperty("user_name")
    private String userName;

    @JsonProperty("total_discount")
    private int totalDiscount;

    @JsonProperty("is_order")
    private boolean isOrder; // 최근 주문 여부

    @JsonProperty("store_picture_url")
    private String storePictureUrl;

    @JsonProperty("pickup_time")
    private String pickupTime;

}
