package com.bit.envdev.controller;

import com.amazonaws.Response;
import com.bit.envdev.dto.CartContentsDTO;
import com.bit.envdev.dto.CartDTO;
import com.bit.envdev.dto.ResponseDTO;
import com.bit.envdev.entity.CustomUserDetails;
import com.bit.envdev.service.CartService;
import com.bit.envdev.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;
    private final PointService pointService;

    @PostMapping("/add")
    public ResponseEntity<?> addCart(@RequestBody CartDTO cartDTO,
                                     @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        ResponseDTO<CartDTO> responseDTO = new ResponseDTO<>();

        try {
            System.out.println(cartDTO.toString());
            // 1. 해당 유저로 카트가 있는 지 조회 Cart의 isPaid가 false인 cart
            int cartCnt = cartService.getCartByMemberId(customUserDetails.getMember().getMemberId());

            CartDTO returnCartDTO = new CartDTO();

            // 2. cartCnt가 0이면 cart 생성하고 0이 아니면 cart에 item 추가
            if(cartCnt == 0) {
                returnCartDTO = cartService.createCart(cartDTO, customUserDetails.getMember());
            } else {
                returnCartDTO = cartService.addCartItem(cartDTO, customUserDetails.getMember());
            }

            responseDTO.setItem(returnCartDTO);
            responseDTO.setStatusCode(HttpStatus.OK.value());
            
            return ResponseEntity.ok(responseDTO);
        } catch(Exception e) {
            System.out.println(e.getMessage());
            if(e.getMessage().equalsIgnoreCase("already exist contents")) {
                responseDTO.setErrorCode(4001);
            } else if(e.getMessage().equalsIgnoreCase("already buy contents")) {
                responseDTO.setErrorCode(4002);
            } else {
                responseDTO.setErrorCode(4003);
            }
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @GetMapping("/cart")
    public ResponseEntity<?> findCart(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        ResponseDTO<Map<String, Object>> responseDTO = new ResponseDTO<>();

        try {
            Map<String, Object> returnMap = new HashMap<>();

            CartDTO cartDTO = cartService.findCartByMemberId(customUserDetails.getMember().getMemberId());
            returnMap.put("cart", cartDTO);

            List<Map<String, String>> cartContentsList = cartService.findCartContentsListByMemberId(cartDTO.getCartId());
            returnMap.put("cartContentsList", cartContentsList);

            long myPoint = pointService.getMyPoint(customUserDetails.getMember().getMemberId());
            returnMap.put("myPoint", myPoint);

            responseDTO.setItem(returnMap);
            responseDTO.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if(e.getMessage().equalsIgnoreCase("not exist cart")) {
                responseDTO.setErrorCode(4003);
            } else {
                responseDTO.setErrorCode(4004);
            }
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @DeleteMapping("/deleteOne/{cartId}")
    public ResponseEntity<?> deleteOne(@PathVariable("cartId") long cartId,
                                       @RequestParam("contentsId") int contentsId,
                                       @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        ResponseDTO<Map<String, Object>> responseDTO = new ResponseDTO<>();

        try {
            cartService.deleteOne(cartId, contentsId);

            Map<String, Object> returnMap = new HashMap<>();

            CartDTO cartDTO = cartService.findCartByMemberId(customUserDetails.getMember().getMemberId());
            returnMap.put("cart", cartDTO);

            List<Map<String, String>> cartContentsList = cartService.findCartContentsListByMemberId(cartDTO.getCartId());
            returnMap.put("cartContentsList", cartContentsList);

            responseDTO.setItem(returnMap);
            responseDTO.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if(e.getMessage().equalsIgnoreCase("not exist cart")) {
                responseDTO.setErrorCode(4003);
            } else {
                responseDTO.setErrorCode(4004);
            }
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<?> delete(@RequestBody List<CartContentsDTO> cartContentsDTOList,
                                       @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        ResponseDTO<Map<String, Object>> responseDTO = new ResponseDTO<>();

        try {
            cartContentsDTOList.forEach(cartContentsDTO ->
                    cartService.deleteOne(cartContentsDTO.getCartId(), cartContentsDTO.getContentsId()));


            Map<String, Object> returnMap = new HashMap<>();

            CartDTO cartDTO = cartService.findCartByMemberId(customUserDetails.getMember().getMemberId());
            returnMap.put("cart", cartDTO);

            List<Map<String, String>> cartContentsList = cartService.findCartContentsListByMemberId(cartDTO.getCartId());
            returnMap.put("cartContentsList", cartContentsList);

            responseDTO.setItem(returnMap);
            responseDTO.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if(e.getMessage().equalsIgnoreCase("not exist cart")) {
                responseDTO.setErrorCode(4003);
            } else {
                responseDTO.setErrorCode(4004);
            }
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }
}
