package com.bit.envdev.repository;

import com.bit.envdev.entity.CartContents;
import com.bit.envdev.entity.CartContentsId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartContentsRepository extends JpaRepository<CartContents, CartContentsId>, CartContentsRepositoryCustom {
}
