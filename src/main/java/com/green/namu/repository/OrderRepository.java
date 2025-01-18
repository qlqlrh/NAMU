package com.green.namu.repository;

import com.green.namu.domain.Order;
import com.green.namu.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findFirstByUserOrderByOrderTimeDesc(User user);
}
