package com.green.namu.repository;

import com.green.namu.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    boolean existsBySetName(String setName);
}
