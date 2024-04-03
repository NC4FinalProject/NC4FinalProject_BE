package com.bit.envdev.repository;

import com.bit.envdev.entity.Member;
import com.bit.envdev.entity.Review;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@Transactional
public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewRepositoryCustom {
    List<Review> findByPayment_CartContents_Contents_ContentsId(int contentsId);

}

