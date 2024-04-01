package com.bit.envdev.contentsController;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.auth.policy.Principal;
import com.bit.envdev.contentsDTO.ContentsDTO;
import com.bit.envdev.contentsDTO.InsertRequestDTO;
import com.bit.envdev.contentsDTO.SectionDTO;
import com.bit.envdev.contentsEntity.Contents;
import com.bit.envdev.contentsService.ContentsService;
import com.bit.envdev.contentsService.impl.ContentsServiceImpl;
import com.bit.envdev.dto.BoardDTO;
import com.bit.envdev.dto.ResponseDTO;
import com.bit.envdev.entity.CustomUserDetails;
import com.bit.envdev.entity.Member;
import com.bit.envdev.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contents")
public class ContentsController {

    private final MemberRepository memberRepository;
    private final ContentsService contentsService;
    
    // 컨텐츠 등록 하기
    @PostMapping("/create")
    public ResponseEntity<?> create (@RequestBody List<SectionDTO> sectionDTO, @AuthenticationPrincipal CustomUserDetails userDetails) {
        ResponseDTO<BoardDTO> responseDTO = new ResponseDTO<>();

        Long memberId = userDetails.getId();

        // InsertRequestDTO insertRequestDTO
        // createRequestDTO에서 ContentsDTO와 List<SectionDTO> 추출
        // ContentsDTO contentsDTO = insertRequestDTO.getContentsDTO();
        // List<SectionDTO> sectionDTO = insertRequestDTO.getSectionDTO();
    
        // Contents createdContents = contentsService.createContents(contentsDTO, memberId);
        // System.out.println("마 뭐가 나오나 함 보까 : " + createdContents);
        System.out.println("마 이게 섹션이다 세꺄 : " + sectionDTO);

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
