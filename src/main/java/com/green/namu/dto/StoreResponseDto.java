package com.green.namu.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreResponseDto {
    private Long storeId;
    private String storeName;
    private String storeCategory;
    private boolean isOpen;
    private String pickupTimes;
    private int minPrice;
    private int reviewCount;
    private int orderCount;
    private double storeRating;
    private int location;
    private String address;
    private String phoneNumber;
    private List<String> storePictureUrls;
    private List<SetNameDto> setNames;
    private List<MenuReadRes> menus;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SetNameDto {
        private String setName;
        private String menuNames;
    }
}