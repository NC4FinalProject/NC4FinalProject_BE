package com.bit.envdev.controller;

import com.bit.envdev.constant.Role;
import com.bit.envdev.dto.PaymentDTO;
import com.bit.envdev.dto.ResponseDTO;
import com.bit.envdev.dto.ReviewDTO;
import com.bit.envdev.entity.CustomUserDetails;
import com.bit.envdev.entity.Member;
import com.bit.envdev.service.PaymentService;
import com.bit.envdev.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")

public class ReviewController {
    private final ReviewService reviewService;
    private final PaymentService paymentService;

    @GetMapping("/review")
    public ResponseEntity<?> getReviewList(@RequestParam("contentsId") int contentsId, @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        String loginMemberNickname = null;
        long loginMemberId = 0;
        List<PaymentDTO> paymentDTOList = null;
        Role loginMemberRole = null;

        if (customUserDetails != null && customUserDetails.getMember() != null) {
            loginMemberNickname = customUserDetails.getMember().getUserNickname();
            loginMemberId = customUserDetails.getId();
            loginMemberRole = customUserDetails.getMember().getRole();
            paymentDTOList = paymentService.getPaymentList(loginMemberId);
        }

        try {
            List<ReviewDTO> reviewDTOList = reviewService.getReviewList(contentsId);

            Map<String, Object> responseData = new HashMap<>();
            responseData.put("reviewList", reviewDTOList);
            responseData.put("loginMemberId",loginMemberId);
            responseData.put("loginMemberNickname", loginMemberNickname);
            responseData.put("loginMemberRole", loginMemberRole);
            responseData.put("paymentList", paymentDTOList);

            return ResponseEntity.ok(responseData);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("errorCode", 303);
            errorResponse.put("errorMessage", e.getMessage());
            errorResponse.put("statusCode", HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @PostMapping("/review")
    public ResponseEntity<?> post(@RequestBody ReviewDTO reviewDTO, @AuthenticationPrincipal CustomUserDetails customUserDetails){

        ResponseDTO<ReviewDTO> responseDTO = new ResponseDTO<>();
        try {
            List<ReviewDTO> reviewDTOList = reviewService.post(reviewDTO, customUserDetails);

            responseDTO.setItems(reviewDTOList);
            responseDTO.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            e.printStackTrace();

            responseDTO.setErrorCode(300);
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @PutMapping("/review")
    public ResponseEntity<?> modify(@RequestBody ReviewDTO reviewDTO, @AuthenticationPrincipal CustomUserDetails customUserDetails){

        ResponseDTO<ReviewDTO> responseDTO = new ResponseDTO<>();
        try {
            List<ReviewDTO> reviewDTOList = reviewService.modify(reviewDTO, customUserDetails);

            responseDTO.setItems(reviewDTOList);
            responseDTO.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            responseDTO.setErrorCode(301);
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @DeleteMapping("/review/{reviewId}")
    public ResponseEntity<?> delete(@PathVariable("reviewId") long reviewId, @RequestParam("contentsId") int contentsId, @AuthenticationPrincipal CustomUserDetails customUserDetails){

        ResponseDTO<ReviewDTO> responseDTO = new ResponseDTO<>();
        try {
            List<ReviewDTO> reviewDTOList = reviewService.delete(reviewId, contentsId, customUserDetails);

            responseDTO.setItems(reviewDTOList);
            responseDTO.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            responseDTO.setErrorCode(302);
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }
}
