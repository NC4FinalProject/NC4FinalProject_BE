package com.bit.envdev.controller;

import com.bit.envdev.dto.ResponseDTO;
import com.bit.envdev.dto.BoardDTO;
import com.bit.envdev.entity.CustomUserDetails;
import com.bit.envdev.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;
    @Operation(method = "POST", summary = "글 작성" , description = "글 작성 API")
    @PostMapping("/board")
    public ResponseEntity<?> save(@RequestBody BoardDTO boardDTO, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        ResponseDTO<BoardDTO> responseDTO = new ResponseDTO<>();

        try {
            boardDTO.setUsername(customUserDetails.getUsername());

            System.out.println("boardDTO 입니다." + boardDTO);;
            // 새로운 일정 DB 저장
            boardService.save(boardDTO);

            // 저장된 일정 목록 다시 호출하여 프론트엔드로 전달
            List<BoardDTO> boardDTOList = boardService.findByUsername(customUserDetails.getUsername());

            responseDTO.setItems(boardDTOList);
            responseDTO.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            responseDTO.setErrorCode(300);
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @GetMapping("/board")
    public ResponseEntity<?> findByUsername(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        ResponseDTO<BoardDTO> responseDTO = new ResponseDTO<>();

        try {
            List<BoardDTO> boardDTOList = boardService.findByUsername(customUserDetails.getUsername());

            responseDTO.setItems(boardDTOList);
            responseDTO.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok(responseDTO);
        } catch(Exception e) {
            responseDTO.setErrorCode(301);
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @PutMapping("/board")
    public ResponseEntity<?> modify(@RequestBody BoardDTO boardDTO, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        ResponseDTO<BoardDTO> responseDTO = new ResponseDTO<>();

        try {
            boardDTO.setUsername(customUserDetails.getUsername());

            boardService.modify(boardDTO);

            List<BoardDTO> boardDTOList = boardService.findByUsername(customUserDetails.getUsername());

            responseDTO.setItems(boardDTOList);
            responseDTO.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok(responseDTO);
        } catch(Exception e) {
            responseDTO.setErrorCode(302);
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @DeleteMapping("/board")
    public ResponseEntity<?> remove(@RequestParam long id, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        ResponseDTO<BoardDTO> responseDTO = new ResponseDTO<>();

        try {
            boardService.remove(id);

            List<BoardDTO> boardDTOList = boardService.findByUsername(customUserDetails.getUsername());

            responseDTO.setItems(boardDTOList);
            responseDTO.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok(responseDTO);
        } catch(Exception e) {
            responseDTO.setErrorCode(303);
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

}
