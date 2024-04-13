package com.bit.envdev.service.impl;

import com.bit.envdev.dto.CartDTO;
import com.bit.envdev.dto.PaymentContentDTO;
import com.bit.envdev.entity.*;
import com.bit.envdev.repository.CartContentsRepository;
import com.bit.envdev.repository.CartRepository;
import com.bit.envdev.repository.ContentsRepository;
import com.bit.envdev.service.CartService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final ContentsRepository contentsRepository;
    private final CartContentsRepository cartContentsRepository;

    @Override
    public int getCartByMemberId(long memberId) {
        return cartRepository.getCartCntByMemberId(memberId);
    }

    @Override
    @Transactional
    //2-1. 카트 생성
    public CartDTO createCart(CartDTO cartDTO, Member member) {
        cartDTO.setMemberId(member.getMemberId());

        Cart cart = cartDTO.toEntity();
        
        // 2-1-1. 이미 결제한 강의일 때는 에러 발생
        if(cartRepository.getCartContentsCntByMemberId(member.getMemberId(), cartDTO.getContentsId()) > 0) {
            System.out.println(cartRepository.getCartContentsCntByMemberId(member.getMemberId(), cartDTO.getContentsId()));
            throw new RuntimeException("already buy contents");
        }

        CartContents cartContents = CartContents.builder()
                .cart(cart)
                .contents(contentsRepository.findById(cartDTO.getContentsId()).orElseThrow())
                .build();

        cart.addCartContentsList(cartContents);

        return cartRepository.save(cart).toDTO();
    }

    @Override
    //2-2. 카트에 아이템 추가
    public CartDTO addCartItem(CartDTO cartDTO, Member member) {
        // 2-2-1. 카트 조회
        Cart cart = cartRepository.getCartByMemberId(member.getMemberId()).orElseThrow();

        // 2-2-2. 카트에 이미 컨텐츠 아이디가 있을 때 에러발생(조회해온 cart와 화면에서 받아온 cartDTO에 있는 contentsId와 비교)
        List<CartContents> cartContentsList = cart.getCartContentsList();

        cartContentsList.forEach(cartContents -> {
            if(cartContents.getContents().getContentsId() == cartDTO.getContentsId()) {
                throw new RuntimeException("already exist contents");
            }
        });

        // 2-2-3. 이미 결제한 강의일 때는 에러 발생
        if(cartRepository.getCartContentsCntByMemberId(member.getMemberId(), cartDTO.getContentsId()) > 0) {
            System.out.println(cartRepository.getCartContentsCntByMemberId(member.getMemberId(), cartDTO.getContentsId()));
            throw new RuntimeException("already buy contents");
        }

        // 2-2-4. 위 에러 발생이 안하면 아이템 추가
        CartContents cartContents = CartContents.builder()
                .cart(cart)
                .contents(contentsRepository.findById(cartDTO.getContentsId()).orElseThrow())
                .build();

        cartContentsRepository.save(cartContents);

        cartContentsRepository.flush();
        cartRepository.flush();

        return cart.toDTO();
    }

    @Override
    public CartDTO findCartByMemberId(long memberId) {
        int cartCnt = cartRepository.getCartCntByMemberId(memberId);

        //cartCnt가 0인 예외 발생
        if(cartCnt == 0) {
            throw new RuntimeException("not exist cart");
        }

        return cartRepository.getCartByMemberId(memberId).orElseThrow().toDTO();
    }

    @Override
    public List<Map<String, String>> findCartContentsListByMemberId(long cartId) {
        return cartRepository.findCartContentsListByMemberId(cartId);
    }

    @Override
    public void deleteOne(long cartId, int contentsId) {
        CartContentsId cartContentsId = new CartContentsId();
        cartContentsId.setCart(cartId);
        cartContentsId.setContents(contentsId);
        cartContentsRepository.delete(cartContentsRepository.findById(cartContentsId).orElseThrow());
    }

    @Override
    public int updateCartContentsPaid(long cartId, List<PaymentContentDTO> paymentContentDTOList) {
        paymentContentDTOList.forEach(paymentContentDTO -> cartContentsRepository.save(CartContents.builder()
                .cart(cartRepository.findById(cartId).orElseThrow())
                .contents(contentsRepository.findById(paymentContentDTO.getContentsId()).orElseThrow())
                .isPaid(true)
                .build()));

        return cartContentsRepository.getCartContentCnt(cartId);
    }

    @Override
    public void updateCartPaid(long cartId, Member member) {
        Cart cart = cartRepository.findById(cartId).orElseThrow();
        cart.setPaid(true);
        cartRepository.save(cart);
    }
}
