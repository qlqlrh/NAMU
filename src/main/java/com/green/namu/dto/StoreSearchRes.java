package com.green.namu.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.green.namu.domain.Menu;
import com.green.namu.domain.Store;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StoreSearchRes {
    private Long storeId;
    private String storeName;
    private String pickupTimes;
    private int minPrice;
    private int reviewCount;
    private int orderCount;
    private int storeRating;
    private int location; // 미터 단위 거리
    private List<MenuSearchRes> setNames;

    public static StoreSearchRes fromEntity(Store store, List<MenuSearchRes> menuSets, int location) {
        return new StoreSearchRes(
                store.getStoreId(),
                store.getStoreName(),
                store.getPickupTime(),
                store.getMenus().stream().mapToInt(Menu::getMenuDiscountPrice).min().orElse(0),
                store.getReviewCount(),
                store.getFavoriteCount(), // 또는 실제 주문 횟수와 매핑된 필드
                store.getStoreRating(),
                location,
                menuSets
        );
    }

}
