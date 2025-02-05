package com.green.namu.repository;

import com.green.namu.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findByUserUserId(Long userId);

    // 주문 완료 후, 해당 cart 레코드 비활성화 처리
    @Modifying
    @Query("UPDATE Cart c SET c.status = 'INACTIVE' WHERE c.user.userId = :userId")
    void updateStatusByUserId(@Param("userId") Long userId);
}
