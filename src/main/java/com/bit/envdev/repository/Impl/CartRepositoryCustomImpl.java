package com.bit.envdev.repository.Impl;

import com.bit.envdev.entity.Cart;
import com.bit.envdev.repository.CartRepositoryCustom;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.*;

import static com.bit.envdev.entity.QCart.cart;
import static com.bit.envdev.entity.QContents.contents;
import static com.bit.envdev.entity.QCartContents.cartContents;

@Repository
@RequiredArgsConstructor
public class CartRepositoryCustomImpl implements CartRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public int getCartCntByMemberId(long memberId) {
        Long cartCnt = jpaQueryFactory
                .select(cart.count())
                .where(cart.member.memberId.eq(memberId).and(cart.isPaid.eq(false)))
                .from(cart)
                .fetchOne();

        return cartCnt.intValue();
    }

    @Override
    public Optional<Cart> getCartByMemberId(long memberId) {
        Cart selectedCart = jpaQueryFactory
                .selectFrom(cart)
                .where(cart.member.memberId.eq(memberId).and(cart.isPaid.eq(false)))
                .fetchOne();

        return Optional.of(selectedCart);
    }

    @Override
    public List<Map<String, String>> findCartContentsListByMemberId(long cartId) {
        List<Tuple> list = jpaQueryFactory
                .select(cartContents, contents)
                .from(cartContents)
                .join(cartContents.contents, contents)
                .where(cartContents.cart.cartId.eq(cartId).and(cartContents.isPaid.eq(false)))
                .fetch();

        List<Map<String, String>> cartContentsList = new ArrayList<>();

        for(Tuple tuple : list) {
            Map<String, String> map = new HashMap<>();

            map.put("cartId", String.valueOf(tuple.get(cartContents).getCart().getCartId()));
            map.put("contentsId", String.valueOf(tuple.get(contents).getContentsId()));
            map.put("contentsTitle", tuple.get(contents).getContentsTitle());
            map.put("price", String.valueOf(tuple.get(contents).getPrice()));
            map.put("priceType", tuple.get(contents).getPriceType());
            map.put("author", tuple.get(contents).getMember().getUserNickname());
            map.put("thumbnail", tuple.get(contents).getThumbnail());

            cartContentsList.add(map);
        }

        return cartContentsList;
    }

    @Override
    public int getCartContentsCntByMemberId(long memberId, int contentsId) {
        return (int)jpaQueryFactory.select(cartContents, cart)
                .from(cartContents)
                .join(cartContents.cart, cart)
                .where(cartContents.contents.contentsId.eq(contentsId).and(cart.member.memberId.eq(memberId)).and(cartContents.isPaid.eq(true)))
                .stream().count();
    }
}
