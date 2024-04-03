package com.bit.envdev.controller;

import java.util.List;

import com.bit.envdev.dto.*;
import com.bit.envdev.entity.Contents;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.bit.envdev.entity.CustomUserDetails;
import com.bit.envdev.repository.MemberRepository;
import com.bit.envdev.service.ContentsService;

import lombok.RequiredArgsConstructor;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/contents")
public class ContentsController {

    private final MemberRepository memberRepository;
    private final ContentsService contentsService;

    // 컨텐츠 등록 하기
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody InsertRequestDTO insertRequestDTO, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Long memberId = customUserDetails.getId();
        ResponseDTO<ContentsDTO> responseDTO = new ResponseDTO<>();
        // 가져온 정보 나눠담기
        ContentsDTO contentsDTO = insertRequestDTO.getContentsDTO();
        List<SectionDTO> sectionDTOList = insertRequestDTO.getSectionDTO();
        log.info("contentsDTO : {}",contentsDTO);
        log.info("sectionDTO : {}",sectionDTOList);
        // 기본 정보 밀어넣기
        Contents createdContents = contentsService.createContents(contentsDTO, memberId);
        // 섹션 정보 밀어넣기
        sectionDTOList.forEach(sectionDTO -> {contentsService.createSection(sectionDTO, createdContents);});
        return null;
    }
    @GetMapping("/detail/{contentsId}")
    public ResponseEntity<?> Detail(@PathVariable("contentsId") int contentsId, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        ResponseDTO<ContentsDTO> responseDTO = new ResponseDTO<>();

        ContentsDTO contentsDTO = contentsService.findById(contentsId);

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

    // // 컨텐츠 상세 보기
    // @GetMapping("/detail/{id}")
    // public ResponseEntity<?> readOne () {
    //     ResponseDTO<BoardDTO> responseDTO = new ResponseDTO<>();
    //     return ResponseEntity.ok(responseDTO);
    // };

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
