package com.bit.envdev.repository.Impl;

import com.bit.envdev.entity.Review;
import com.bit.envdev.repository.ReviewRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryCustomImpl implements ReviewRepositoryCustom {
    private final EntityManager entityManager;
    private final JPAQueryFactory queryFactory;

    @Override
    public void post(Review review) {
        entityManager.merge(review);
    }

    @Override
    public void modify(Review review) {
        entityManager.merge(review);
    }

}
