package com.green.namu.repository;

import com.green.namu.domain.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    Optional<Favorite> findByUserUserIdAndStoreStoreId(Long userId, Long storeId);
    List<Favorite> findAllByUserUserId(Long userId);
}
