package com.bit.envdev.controller;

import com.bit.envdev.dto.ResponseDTO;
import com.bit.envdev.dto.ReviewDTO;
import com.bit.envdev.entity.CustomUserDetails;
import com.bit.envdev.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")

public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/review")
    public ResponseEntity<?> post(@RequestBody ReviewDTO reviewDTO){

        ResponseDTO<ReviewDTO> responseDTO = new ResponseDTO<>();
        try {
            List<ReviewDTO> reviewDTOList = reviewService.post(reviewDTO);

            responseDTO.setItems(reviewDTOList);
            responseDTO.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
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
            responseDTO.setErrorCode(301);
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }
}
