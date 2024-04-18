package com.bit.envdev.repository;

import com.bit.envdev.entity.Review;
import jakarta.transaction.Transactional;

@Transactional
public interface ReviewRepositoryCustom {
    void post(Review review);

    void modify(Review review);

}
