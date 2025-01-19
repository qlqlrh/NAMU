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
    @JsonProperty("store_id")
    private Long storeId;

    @JsonProperty("store_name")
    private String storeName;

    @JsonProperty("pickup_times")
    private String pickupTimes;

    @JsonProperty("min_price")
    private int minPrice;

    @JsonProperty("review_count")
    private int reviewCount;

    @JsonProperty("order_count")
    private int orderCount;

    @JsonProperty("store_rating")
    private int storeRating;

    @JsonProperty("location")
    private int location; // 미터 단위 거리

    @JsonProperty("set_names")
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
