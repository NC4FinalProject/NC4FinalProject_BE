package com.bit.envdev.controller;

import com.bit.envdev.dto.PaymentContentDTO;
import com.bit.envdev.dto.PaymentDTO;
import com.bit.envdev.dto.ResponseDTO;
import com.bit.envdev.dto.ReviewDTO;
import com.bit.envdev.entity.CustomUserDetails;
import com.bit.envdev.service.CartService;
import com.bit.envdev.service.PaymentService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")

public class PaymentController {

    private final PaymentService paymentService;
    private final CartService cartService;

    @GetMapping("/payment")
    public ResponseEntity<?> getPaymentList(@AuthenticationPrincipal CustomUserDetails customUserDetails) {

        ResponseDTO<PaymentDTO> responseDTO = new ResponseDTO<>();
        long loginMemberId = customUserDetails.getMember().getMemberId();
        try {
            List<PaymentDTO> paymentDTOList = paymentService.getPaymentList(loginMemberId);

            responseDTO.setItems(paymentDTOList);
            responseDTO.setStatusCode(HttpStatus.OK.value());
            return ResponseEntity.ok(responseDTO);

        } catch (Exception e) {
            responseDTO.setErrorCode(401);
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @PostMapping("/success")
    public ResponseEntity<?> paymentSuccess(@RequestPart("paymentDTO") PaymentDTO paymentDTO,
                                            @RequestPart("contentsList")List<PaymentContentDTO> paymentContentDTOList,
                                            @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        ResponseDTO<PaymentDTO> responseDTO = new ResponseDTO<>();
        try {
            // 1. payment 테이블에 저장
            long paymentId = paymentService.savePayment(paymentDTO, paymentContentDTOList, customUserDetails.getMember().getMemberId());

            // 2. cartContents 테이블에 ispaid true로 변경
            int cartContentCnt = cartService.updateCartContentsPaid(paymentDTO.getCartId(), paymentContentDTOList);
            System.out.println("======================================");
            System.out.println(cartContentCnt);
            // 3. cartContents 테이블에 ispaid false인 게 0개면 cart 테이블 ispaid true로 변경
            if (cartContentCnt == 0) {
                cartService.updateCartPaid(paymentDTO.getCartId(), customUserDetails.getMember());
            }

            PaymentDTO returnPaymentDTO = paymentService.getPayment(paymentId, customUserDetails.getMember().getMemberId());

            responseDTO.setItem(returnPaymentDTO);
            responseDTO.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok(responseDTO);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            responseDTO.setErrorCode(401);
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }
}
