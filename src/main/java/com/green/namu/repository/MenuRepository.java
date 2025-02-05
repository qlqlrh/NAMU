package com.green.namu.repository;

import com.green.namu.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    boolean existsBySetName(String setName);

    // 특정 가게의 메뉴 목록을 조회하는 메서드
    List<Menu> findByStore_StoreId(Long storeId);
}
