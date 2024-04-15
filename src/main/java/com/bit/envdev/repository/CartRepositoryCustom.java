package com.bit.envdev.repository;

import com.bit.envdev.entity.Cart;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CartRepositoryCustom {
    int getCartCntByMemberId(long memberId);

    Optional<Cart> getCartByMemberId(long memberId);

    List<Map<String, String>> findCartContentsListByMemberId(long cartId);

    int getCartContentsCntByMemberId(long memberId, int contentsId);
}
