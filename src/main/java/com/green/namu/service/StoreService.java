package com.green.namu.service;

import com.green.namu.common.exceptions.BaseException;
import com.green.namu.common.response.BaseResponseStatus;
import com.green.namu.domain.Menu;
import com.green.namu.domain.Store;
import com.green.namu.dto.MenuSearchRes;
import com.green.namu.dto.StoreSearchRes;
import com.green.namu.dto.StoreResponseDto;
import com.green.namu.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    // 카테고리 한글 -> 영어 매핑
    private static final Map<String, String> CATEGORY_TRANSLATIONS = Map.of(
            "샌드위치", "SANDWICHE",
            "과일", "FRUIT",
            "편의점", "MART",
            "마트", "MART",
            "디저트", "DESSERT"
    );

    public List<StoreSearchRes> searchStores(String term, String option) {
        // 검색어가 카테고리인 경우, 영어 이름으로 변환 (해당되지 않을 경우 그대로 사용)
        String translatedTerm = CATEGORY_TRANSLATIONS.getOrDefault(term, term);

        // 검색어 기반으로 Store 목록 가져오기
        List<Store> stores = storeRepository.searchByTerm(translatedTerm);
        if (stores.isEmpty()) {
            throw new BaseException(BaseResponseStatus.SEARCH_NO_RESULTS);
        }

        // 현재는 데이터 크기가 작기 때문에 서비스 쪽에서 정렬 처리했음
        stores.sort((s1, s2) -> {
           switch (option) {
               case "rating":
                   return Double.compare(s2.getStoreRating(), s1.getStoreRating());
               case "distance":
                   // TODO: 거리 계산 로직 추가
                   return 0;
               case "like":
                   return Integer.compare(s2.getFavoriteCount(), s1.getFavoriteCount());
               case "order":
                   return Integer.compare(s2.getOrderCount(), s1.getOrderCount());
               case "price":
                   return Integer.compare(
                           s1.getMenus().stream().mapToInt(Menu::getMenuDiscountPrice).min().orElse(0),
                           s2.getMenus().stream().mapToInt(Menu::getMenuDiscountPrice).min().orElse(0)
                   );
               default: // normal
                   return Long.compare(s1.getStoreId(), s2.getStoreId());
           }
        });

        // 결과 매핑 및 반환
        return stores.stream()
                .map(store -> {
                    // Store에 속한 메뉴 리스트를 MenuSearchResponse 형태로 반환
                    List<MenuSearchRes> menuSets = store.getMenus().stream()
                            .map(menu -> new MenuSearchRes(menu.getSetName(), menu.getMenuNames()))
                            .collect(Collectors.toList());

                    // StoreSearchResponse 객체 생성
                    return StoreSearchRes.fromEntity(store, menuSets, 1900); // TODO: 거리는 일단 더미 값
                })
                .collect(Collectors.toList());
    }

    public List<StoreResponseDto> getAllStores() {
        return storeRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public StoreResponseDto getStoreById(Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("Store not found"));
        return convertToDto(store);
    }

    private StoreResponseDto convertToDto(Store store) {
        return StoreResponseDto.builder()
                .storeId(store.getStoreId())
                .storeName(store.getStoreName())
                .storeCategory(store.getStoreCategory().name())
                .isOpen(store.isOpen())
                .pickupTimes(store.getPickupTime())
                .minPrice(store.getMinPrice())
                .reviewCount(store.getReviewCount())
                .orderCount(store.getOrderCount())
                .storeRating(store.getStoreRating())
                .location(store.getLocation())
                .setNames(store.getSetNames().stream()
                        .map(setName -> StoreResponseDto.SetNameDto.builder()
                                .setName(setName.getSetName())
                                .menuNames(setName.getMenuNames())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
