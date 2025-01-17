package com.green.namu.controller;

import com.green.namu.dto.StoreResponseDto;
import com.green.namu.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;

    @GetMapping("/list")
    public List<StoreResponseDto> getAllStores() {
        return storeService.getAllStores();
    }

    @GetMapping("/{storeId}")
    public StoreResponseDto getStoreById(@PathVariable Long storeId) {
        return storeService.getStoreById(storeId);
    }
}