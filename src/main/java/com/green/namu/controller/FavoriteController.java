package com.green.namu.controller;

import com.green.namu.dto.FavoriteDto;
import com.green.namu.service.FavoriteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/store/like")
@Tag(name = "찜", description = "찜 관련 API")
public class FavoriteController {

    private final FavoriteService favoriteService;

    @Operation(summary = "찜한 전체 가게 조회")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<FavoriteDto> getAllFavorites(@RequestParam Long userId) {
        return favoriteService.getAllFavorites(userId);
    }

    @Operation(summary = "찜한 가게 추가")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addFavorite(@RequestBody FavoriteDto favoriteDto) {
        favoriteService.addFavorite(favoriteDto);
    }

    @Operation(summary = "찜한 가게 취소")
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeFavorite(@RequestBody FavoriteDto favoriteDto) {
        favoriteService.removeFavorite(favoriteDto);
    }
}
