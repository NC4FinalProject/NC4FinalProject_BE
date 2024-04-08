package com.bit.envdev.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bit.envdev.common.FileUtils;
import com.bit.envdev.dto.*;
import com.bit.envdev.entity.Contents;
import com.bit.envdev.entity.Video;
import com.bit.envdev.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.bit.envdev.entity.CustomUserDetails;
import com.bit.envdev.repository.MemberRepository;
import com.bit.envdev.service.ContentsService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/contents")
public class ContentsController {

    private final ContentsService contentsService;
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
            // 현재 처리되는 비디오 파일에 대한 정보 출력
            MultipartFile file = videoFile[i];
            System.out.println("파일 이름: " + file.getOriginalFilename());
            System.out.println("파일 크기: " + file.getSize() + " bytes");
            System.out.println("파일 타입: " + file.getContentType());
            System.out.println("-------------------------------");

        }
        // 섹션 정보 밀어넣기
        sectionDTOList.forEach(sectionDTO -> {contentsService.createSection(sectionDTO, createdContents);});
        return null;
    }
    // // 컨텐츠 상세 보기
    @GetMapping("/detail/{contentsId}")
    public ResponseEntity<?> Detail(@PathVariable(name = "contentsId") int contentsId, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        ResponseDTO<ContentsDTO> responseDTO = new ResponseDTO<>();
        ContentsDTO contentsDTO = contentsService.findById(contentsId);
        responseDTO.setItem(contentsDTO);
        return ResponseEntity.ok(responseDTO);
    }
}
