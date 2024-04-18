package com.bit.envdev.controller;

import com.bit.envdev.dto.QnaDTO;
import com.bit.envdev.dto.ResponseDTO;
import com.bit.envdev.service.QnaService;
import com.bit.envdev.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final ReviewService reviewService;
    private final QnaService qnaService;
    @RequestMapping("/")
    private ResponseEntity<?> getMainContents() {
        Map<String, Object> result= new HashMap<>();
        try {
            List<Object[]> RecentContentsList  = reviewService.get12RecentContents();
            List<Object[]> BestContentsList  = reviewService.get12BestContents();
            List<Object[]> RandomContentsList = reviewService.get12RandomContents();
            List<Object[]> RecentCommentList = reviewService.get10RecentComments();
          result.put("bestContents", BestContentsList);
          result.put("recentContents", RecentContentsList);
          result.put("randomContents", RandomContentsList);
          result.put("recentComment", RecentCommentList);
          return ResponseEntity.ok(result);
        } catch (Exception e) {
            result.put("id", "에러");
            result.put("name", "에러");
            result.put("profile", "default.png");
            return ResponseEntity.ok(result);
        }
    }
    @PostMapping("/sendqna")
    private ResponseEntity<?> sendQna(@RequestBody QnaDTO qnaDTO) {
        ResponseDTO<QnaDTO> responseDTO = new ResponseDTO<>();
        try {
            qnaService.sendQna(qnaDTO);
            return ResponseEntity.ok(null);
        } catch (Exception e) {
            responseDTO.setErrorCode(102);
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }
}