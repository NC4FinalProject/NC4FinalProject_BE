package com.bit.envdev.controller;

import com.bit.envdev.common.FileUtils;
import com.bit.envdev.dto.FileDTO;
import com.bit.envdev.dto.NoticeDTO;
import com.bit.envdev.dto.ResponseDTO;
import com.bit.envdev.entity.CustomUserDetails;
import com.bit.envdev.entity.Member;
import com.bit.envdev.service.MemberService;
import com.bit.envdev.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController {
    private final NoticeService noticeService;
    private final FileUtils fileUtils;
    private final MemberService memberService;

    @GetMapping("/notice-list")
    public ResponseEntity<?> getBoardList(@PageableDefault(page = 0, size = 15) Pageable pageable,
                                          @RequestParam("searchCondition") String searchCondition,
                                          @RequestParam("searchKeyword") String searchKeyword) {
        ResponseDTO<NoticeDTO> responseDTO = new ResponseDTO<>();

        try {
            Page<NoticeDTO> noticeDTOPage = noticeService.searchAll(pageable, searchCondition, searchKeyword);

            responseDTO.setPageItems(noticeDTOPage);
            responseDTO.setItem(NoticeDTO.builder()
                    .searchCondition(searchCondition)
                    .searchKeyword(searchKeyword)
                    .build());
            responseDTO.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok(responseDTO);
        } catch(Exception e) {
            responseDTO.setErrorCode(401);
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());

            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @PostMapping("/notice")
    public ResponseEntity<?> postNoticeWithImage(@RequestBody NoticeDTO noticeDTO,
                                                 @RequestParam(value = "uploadFiles", required = false) MultipartFile[] multipartFiles,
                                                 @PageableDefault(page = 0, size = 10) Pageable pageable) {
        ResponseDTO<NoticeDTO> responseDTO = new ResponseDTO<>();

        try {
            List<FileDTO> noticeFileDTOList = new ArrayList<>();

            // 이미지 파일이 전송되었을 때 처리
            if(multipartFiles != null) {
                Arrays.stream(multipartFiles).forEach(multipartFile -> {
                    if (multipartFile.getOriginalFilename() != null &&
                            !multipartFile.getOriginalFilename().equalsIgnoreCase("")) {
                        FileDTO noticeFile = fileUtils.parseFileInfo(multipartFile, "notice/");
                        noticeFileDTOList.add(noticeFile);
                    }
                });
            }

            noticeDTO.setNoticeFileDTOList(noticeFileDTOList);

            noticeService.post(noticeDTO);

            // 페이지네이션된 모든 글 불러오기
            Page<NoticeDTO> noticeDTOPage = noticeService.searchAll(pageable, "all", "");

            responseDTO.setPageItems(noticeDTOPage);
            responseDTO.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok(responseDTO);
        } catch(Exception e) {
            responseDTO.setErrorCode(500);
            responseDTO.setErrorMessage("Internal server error: " + e.getMessage());
            responseDTO.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
        }
    }

    @GetMapping("/notice/{noticeNo}")
    public ResponseEntity<?> getNotice(@PathVariable("noticeNo") Long noticeNo) {
        ResponseDTO<NoticeDTO> responseDTO = new ResponseDTO<>();
        try {
            System.out.println("여기까지 오나");
            NoticeDTO noticeDTO = noticeService.findById(noticeNo);
            System.out.println(noticeDTO.getNoticeWriter());
            String profileImageUrl = memberService.getProfileImageUrl(noticeDTO.getNoticeWriter());
            System.out.println(profileImageUrl);
            noticeDTO.setProfileImageUrl(profileImageUrl);
            System.out.println("--------------------------------");

            responseDTO.setItem(noticeDTO);
            responseDTO.setStatusCode(HttpStatus.OK.value());
            System.out.println("보내짐");

            return ResponseEntity.ok(responseDTO);
        } catch(Exception e) {
            responseDTO.setErrorCode(404);
            responseDTO.setErrorMessage("Notice not found: " + e.getMessage());
            responseDTO.setStatusCode(HttpStatus.NOT_FOUND.value());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
        }
    }

}
