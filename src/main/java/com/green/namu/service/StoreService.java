package com.green.namu.service;

import com.green.namu.domain.Store;
import com.green.namu.dto.StoreResponseDto;
import com.green.namu.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;

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
                .pickupTimes(store.getPickupTimes())
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
