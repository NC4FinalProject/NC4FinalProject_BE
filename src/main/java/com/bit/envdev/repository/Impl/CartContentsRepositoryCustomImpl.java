package com.bit.envdev.repository.Impl;

import com.bit.envdev.repository.CartContentsRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import static com.bit.envdev.entity.QCartContents.cartContents;

@Repository
@RequiredArgsConstructor
public class CartContentsRepositoryCustomImpl implements CartContentsRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public int getCartContentCnt(long cartId) {
        return jpaQueryFactory.select(cartContents.count())
                .from(cartContents)
                .where(cartContents.cart.cartId.eq(cartId).and(cartContents.isPaid.eq(false)))
                .fetchOne().intValue();
    }
}
