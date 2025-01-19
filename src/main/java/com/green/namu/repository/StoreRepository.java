package com.green.namu.repository;

import com.green.namu.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    /**
     * 카테고리, 가게 이름, 메뉴 이름, 세트 이름을 검색하는 쿼리
     */
    @Query("SELECT DISTINCT s "+
            "FROM Store s LEFT JOIN FETCH s.menus m " +
            "WHERE LOWER(s.storeName) LIKE LOWER(CONCAT('%', :term, '%')) " +
            "OR LOWER(m.menuNames) LIKE LOWER(CONCAT('%', :term, '%')) " +
            "OR LOWER(m.setName) LIKE LOWER(CONCAT('%', :term, '%'))" +
            "OR LOWER(s.storeCategory) LIKE LOWER(CONCAT('%', :term, '%'))" +
            "OR LOWER(m.menuCategory) LIKE LOWER(CONCAT('%', :term, '%'))"
    )
    List<Store> searchByTerm(@Param("term") String term);
}
