package com.bit.envdev.service;

import com.bit.envdev.dto.CartDTO;
import com.bit.envdev.dto.PaymentContentDTO;
import com.bit.envdev.entity.Member;

import java.util.List;
import java.util.Map;

public interface CartService {
    int getCartByMemberId(long memberId);

    CartDTO createCart(CartDTO cartDTO, Member member);

    CartDTO addCartItem(CartDTO cartDTO, Member member);

    CartDTO findCartByMemberId(long memberId);

    List<Map<String, String>> findCartContentsListByMemberId(long cartId);

    void deleteOne(long cartId, int contentsId);

    int updateCartContentsPaid(long cartId, List<PaymentContentDTO> paymentContentDTOList);

    void updateCartPaid(long cartId, Member member);
}
