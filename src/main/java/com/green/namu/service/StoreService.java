package com.green.namu.service;

import com.green.namu.common.exceptions.BaseException;
import com.green.namu.common.response.BaseResponseStatus;
import com.green.namu.domain.Store;
import com.green.namu.dto.MenuSearchResponse;
import com.green.namu.dto.StoreSearchResponse;
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

    public List<StoreSearchResponse> searchStoresByTerm(String term) {
        // 검색어가 카테고리인 경우, 영어 이름으로 변환 (해당되지 않을 경우 그대로 사용)
        String translatedTerm = CATEGORY_TRANSLATIONS.getOrDefault(term, term);

        // 검색어 기반으로 Store 목록 가져오기
        List<Store> stores = storeRepository.searchByTerm(translatedTerm);
        if (stores.isEmpty()) {
            throw new BaseException(BaseResponseStatus.SEARCH_NO_RESULTS);
        }

        // 결과 매핑 및 반환
        return stores.stream()
                .map(store -> {
                    // Store에 속한 메뉴 리스트를 MenuSearchResponse 형태로 반환
                    List<MenuSearchResponse> menuSets = store.getMenus().stream()
                            .map(menu -> new MenuSearchResponse(menu.getSetName(), menu.getMenuNames()))
                            .collect(Collectors.toList());

                    // StoreSearchResponse 객체 생성
                    return StoreSearchResponse.fromEntity(store, menuSets, 1900); // TODO: 거리는 일단 더미 값
                })
                .collect(Collectors.toList());
    }
}
