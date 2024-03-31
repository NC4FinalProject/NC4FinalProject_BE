package com.bit.envdev.contentsController;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bit.envdev.contentsDTO.ContentsDTO;
import com.bit.envdev.dto.BoardDTO;
import com.bit.envdev.dto.ResponseDTO;
import com.bit.envdev.entity.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contents")
public class ContentsController {

    // 컨텐츠 전체 보기
    @GetMapping("/listAll")
    public ResponseEntity<?> readAll () {
        ResponseDTO<ContentsDTO> responseDTO = new ResponseDTO<>();
        // 서비스 로직
        System.out.println("일단 호출만 하자");
        return ResponseEntity.ok(responseDTO);
    }

    // 컨텐츠 상세 보기
    @GetMapping("/detail/{id}")
    public ResponseEntity<?> readOne () {
        ResponseDTO<BoardDTO> responseDTO = new ResponseDTO<>();
        return ResponseEntity.ok(responseDTO);
    };

    // 컨텐츠 등록 하기
    @PostMapping("/create")
    public ResponseEntity<?> create () {
        ResponseDTO<BoardDTO> responseDTO = new ResponseDTO<>();
        return ResponseEntity.ok(responseDTO);
    }

    // 컨텐츠 수정 하기
    // @PostMapping("/update/{id}")
    // public ResponseEntity<?> update () {
    //     ResponseDTO<BoardDTO> responseDTO = new ResponseDTO<>();
    //     return ResponseEntity.ok(responseDTO);
    // }

    // 컨텐츠 삭제 하기
    // @DeleteMapping("/delete/{id}")
    // public ResponseEntity<?> delete () {
    //     ResponseDTO<> responseDTO = new ResponseDTO<>();
    // }

}
