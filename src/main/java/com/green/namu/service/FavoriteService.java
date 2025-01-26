package com.green.namu.service;

import com.green.namu.domain.Favorite;
import com.green.namu.domain.Store;
import com.green.namu.domain.User;
import com.green.namu.dto.FavoriteDto;
import com.green.namu.repository.FavoriteRepository;
import com.green.namu.common.exceptions.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.green.namu.common.response.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class FavoriteService {
    private final FavoriteRepository favoriteRepository;

    public List<FavoriteDto> getAllFavorites(Long userId) {
        return favoriteRepository.findAllByUserUserId(userId).stream()
                .map(favorite -> new FavoriteDto(favorite.getUser().getUserId(), favorite.getStore().getStoreId()))
                .collect(Collectors.toList());
    }

    public void addFavorite(FavoriteDto favoriteDto) {
        if (favoriteRepository.findByUserUserIdAndStoreStoreId(favoriteDto.getUserId(), favoriteDto.getStoreId()).isPresent()) {
            throw new BaseException(ALREADY_LIKED_STORE);
        }

        User user = User.builder()
                .userId(favoriteDto.getUserId())
                .build();

        Store store = Store.builder()
                .storeId(favoriteDto.getStoreId())
                .build();

        Favorite favorite = Favorite.builder()
                .user(user)
                .store(store)
                .isFavorite(true)
                .build();

        favoriteRepository.save(favorite);
    }

    public void removeFavorite(FavoriteDto favoriteDto) {
        Favorite favorite = favoriteRepository.findByUserUserIdAndStoreStoreId(favoriteDto.getUserId(), favoriteDto.getStoreId())
                .orElseThrow(() -> new BaseException(LIKE_NOT_FOUND));
        favoriteRepository.delete(favorite);
    }
}
