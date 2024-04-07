package com.bit.envdev.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bit.envdev.common.FileUtils;
import com.bit.envdev.dto.*;
import com.bit.envdev.entity.Contents;
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
    private final MemberService memberService;
    private final FileUtils fileUtils;

    // 컨텐츠 등록 하기
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestPart("insertRequestDTO") InsertRequestDTO insertRequestDTO,
                                    @RequestPart("thumbnail") MultipartFile thumbnail,
                                    @RequestPart("videoFile") List<MultipartFile> videoFile,
                                    @AuthenticationPrincipal CustomUserDetails customUserDetails) throws IOException {

        Long memberId = customUserDetails.getId();
//        ResponseDTO<ContentsDTO> responseDTO = new ResponseDTO<>();
        ResponseDTO<Map<String, String>> responseDTO = new ResponseDTO<>();

        // 가져온 정보 나눠담기
        ContentsDTO contentsDTO = insertRequestDTO.getContentsDTO();
        List<SectionDTO> sectionDTOList = insertRequestDTO.getSectionDTO();
        List<VideoDTO> videoDTOList = insertRequestDTO.getVideoDTO();
        /////////////////////////////////////////////////////////////////
        log.info("contentsDTO : {}",contentsDTO);
        log.info("sectionDTO : {}",sectionDTOList);
        log.info("videoDTO : {}",videoDTOList);

        ObjectMapper objectMapper = new ObjectMapper();

        // 객체를 JSON 문자열로 변환하여 로깅
        String json = objectMapper.writeValueAsString(insertRequestDTO);
        log.info(json);
        /////////////////////////////////////////////////////////////////
        String fileString = null;
        if (thumbnail.getOriginalFilename() != null && !thumbnail.getOriginalFilename().isEmpty()) {
            FileDTO fileDTO = fileUtils.uploadThumbnail(thumbnail, "board/");  // 오브젝트 스토리지에 업로드하면서 만들어진 디티오를 파일 디티오에 담음
            fileString = (fileDTO.getItemFilePath()+fileDTO.getItemFileName()); // 파일 디티오에 경로와 파일 디티오에 파일 네임을 담은 fileString에 담음
            System.out.println("컨트롤러? 오나요?");
            System.out.println(fileString);
        }

        // 기본 정보 밀어넣기
        Contents createdContents = contentsService.createContents(contentsDTO, memberId, fileString);
        // 비디오 정보 밀어넣기
        videoDTOList.forEach(videoDTO -> {contentsService.createVideo(videoDTO, createdContents, memberId);});
        // 섹션 정보 밀어넣기
        sectionDTOList.forEach(sectionDTO -> {contentsService.createSection(sectionDTO, createdContents);});
        // 썸네일 정보 밀어넣기

        return null;
    }



    // // 컨텐츠 상세 보기
    @GetMapping("/detail/{contentsId}")
    public ResponseEntity<?> Detail(@PathVariable(name = "contentsId") int contentsId, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        ResponseDTO<ContentsDTO> responseDTO = new ResponseDTO<>();
        System.out.println("여기 오시나유?");

        ContentsDTO contentsDTO = contentsService.findById(contentsId);
//        SectionDTO sectionDTO = contentsService.findById(sectionId);
//        VideoDTO videoDTO = contentsService.findById(videoId);

        responseDTO.setItem(contentsDTO);

        return ResponseEntity.ok(responseDTO);
    }



    // // 컨텐츠 전체 보기
    // @GetMapping("/listAll")
    // public ResponseEntity<?> readAll () {
    //     ResponseDTO<ContentsDTO> responseDTO = new ResponseDTO<>();
    //     // 서비스 로직
    //     System.out.println("일단 호출만 하자");
    //     return ResponseEntity.ok(responseDTO);
    // }

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
