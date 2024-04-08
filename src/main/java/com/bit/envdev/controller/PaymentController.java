package com.bit.envdev.controller;

import com.bit.envdev.dto.PaymentDTO;
import com.bit.envdev.dto.ResponseDTO;
import com.bit.envdev.dto.ReviewDTO;
import com.bit.envdev.entity.CustomUserDetails;
import com.bit.envdev.service.PaymentService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")

public class PaymentController {

    final PaymentService paymentService;

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
}
