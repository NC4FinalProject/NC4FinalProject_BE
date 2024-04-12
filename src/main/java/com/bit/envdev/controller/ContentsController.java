package com.bit.envdev.controller;


import java.io.IOException;
import java.util.List;
import com.bit.envdev.dto.*;
import com.bit.envdev.entity.Contents;
import com.bit.envdev.entity.VideoReply;
import java.util.HashMap;
import java.util.Map;


import com.bit.envdev.service.ContentsBookmarkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.bit.envdev.entity.CustomUserDetails;
import com.bit.envdev.service.ContentsService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/contents")
public class ContentsController {

    private final ContentsService contentsService;
    private final ContentsBookmarkService contentsBookmarkService;


    // 컨텐츠 등록 하기
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestPart("insertRequestDTO") InsertRequestDTO insertRequestDTO,
                                    @RequestPart("thumbnail") MultipartFile thumbnail,
                                    @RequestPart("videoFile") MultipartFile[] videoFile,
                                    @AuthenticationPrincipal CustomUserDetails customUserDetails) throws IOException {
        ResponseDTO<ContentsDTO> responseDTO = new ResponseDTO<>();
        Long memberId = customUserDetails.getId();
        // 가져온 정보 나눠담기
        ContentsDTO contentsDTO = insertRequestDTO.getContentsDTO();
        List<SectionDTO> sectionDTOList = insertRequestDTO.getSectionDTO();
        List<VideoDTO> videoDTOList = insertRequestDTO.getVideoDTO();
        // 기본 정보 밀어넣기, 썸네일 파일 밀어넣기
        Contents createdContents = contentsService.createContents(contentsDTO, memberId, thumbnail );
        // 비디오 정보 밀어넣기, 동영상 파일 밀어 넣기
        for (int i = 0; i < videoDTOList.size(); i++) {
            contentsService.createVideo(videoDTOList.get(i), createdContents, memberId, videoFile[i]);
        }
        // 섹션 정보 밀어넣기
        sectionDTOList.forEach(sectionDTO -> {contentsService.createSection(sectionDTO, createdContents);});
        return null;
    }
    // // 컨텐츠 상세 보기
    @GetMapping("/detail/{contentsId}")
    public ResponseEntity<?> Detail(@PathVariable(name = "contentsId") int contentsId) {
        ResponseDTO<ContentsDTO> responseDTO = new ResponseDTO<>();
        ContentsDTO contentsDTO = contentsService.findById(contentsId);
        responseDTO.setItem(contentsDTO);
        return ResponseEntity.ok(responseDTO);
    }

    // // 컨텐츠 목록 보기
    @GetMapping("/list")
    public ResponseEntity<?> listContents() {
        // ResponseDTO 객체 생성
        ResponseDTO<ContentsDTO> responseDTO = new ResponseDTO<>();
        // contentsService에서 모든 컨텐츠를 조회하여 ContentsDTO 리스트로 가져옴
        List<ContentsDTO> contentsDTOList = contentsService.findAll();
        responseDTO.setItems(contentsDTOList);
        // ResponseDTO 객체를 ResponseEntity를 통해 클라이언트에게 반환
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/detail/saveVideoReply")
    public ResponseEntity<?> saveVideoReply(@RequestBody VideoReplyDTO videoReplyDTO,
                                    @AuthenticationPrincipal CustomUserDetails customUserDetails) throws IOException {
        ResponseDTO<ContentsDTO> responseDTO = new ResponseDTO<>();
        Long memberId = customUserDetails.getId();
        videoReplyDTO.setMemberId(memberId);

        // 비디오별 댓글 저장하기
        VideoReply videoReply = contentsService.saveVideoReply(videoReplyDTO);
        System.out.println(videoReplyDTO);
        System.out.println(memberId);

        return null;
    }


    @GetMapping("/bookmark")
    public ResponseEntity<?> getBookmarkContents(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        ResponseDTO<List<ContentsBookmarkDTO>> responseDTO = new ResponseDTO<>();
        try {
            List<ContentsBookmarkDTO> bookmarkContents = contentsBookmarkService.getBookmarkContents(customUserDetails.getMember().getMemberId());
            responseDTO.setItem(bookmarkContents);
            responseDTO.setStatusCode(HttpStatus.OK.value());
            return ResponseEntity.ok(responseDTO);
        } catch(Exception e) {
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setErrorCode(501);
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(responseDTO);
        }

    }

    @PostMapping("/bookmark")
    public ResponseEntity<?> addBookmark(@RequestBody ContentsBookmarkDTO contentsBookmarkDTO,@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        ResponseDTO<Map<String, String>> responseDTO = new ResponseDTO<>();
        try {
            System.out.println("=============================");
            System.out.println(contentsBookmarkDTO);
            contentsBookmarkService.addBookmark(contentsBookmarkDTO, customUserDetails.getMember());
            Map<String, String> msgMap = new HashMap<>();
            msgMap.put("msg", "Bookmark add");
            responseDTO.setItem(msgMap);
            responseDTO.setStatusCode(HttpStatus.OK.value());
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setErrorCode(502);
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @DeleteMapping("bookmark/{contentsId}")
    public ResponseEntity<?> removeBookmark(@PathVariable int contentsId, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        ResponseDTO<Map<String, String>> responseDTO = new ResponseDTO<>();
        try {
            contentsBookmarkService.removeBookmark(contentsId, customUserDetails.getMember().getMemberId());
            Map<String, String> msgMap = new HashMap<>();
            msgMap.put("msg", "Bookmark remove");
            responseDTO.setItem(msgMap);
            responseDTO.setStatusCode(HttpStatus.OK.value());
            return ResponseEntity.ok(responseDTO);
        } catch(Exception e) {
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setErrorCode(503);
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(responseDTO);

        }
    }
}

