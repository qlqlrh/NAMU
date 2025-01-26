package com.green.namu.controller;

import com.green.namu.dto.FavoriteDto;
import com.green.namu.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/store/like")
public class FavoriteController {

    private final FavoriteService favoriteService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<FavoriteDto> getAllFavorites(@RequestParam Long userId) {
        return favoriteService.getAllFavorites(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addFavorite(@RequestBody FavoriteDto favoriteDto) {
        favoriteService.addFavorite(favoriteDto);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeFavorite(@RequestBody FavoriteDto favoriteDto) {
        favoriteService.removeFavorite(favoriteDto);
    }
}
