package com.bit.envdev.repository;

import com.bit.envdev.entity.Member;
import com.bit.envdev.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {


}
