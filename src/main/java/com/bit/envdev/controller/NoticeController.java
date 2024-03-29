package com.bit.envdev.controller;

import com.bit.envdev.common.FileUtils;
import com.bit.envdev.dto.FileDTO;
import com.bit.envdev.dto.NoticeDTO;
import com.bit.envdev.dto.ResponseDTO;
import com.bit.envdev.service.MemberService;
import com.bit.envdev.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController {
    private final NoticeService noticeService;
    private final FileUtils fileUtils;
    private final MemberService memberService;
    private List<String> temporaryImage = new ArrayList<>();

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
        } catch (Exception e) {
            responseDTO.setErrorCode(401);
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());

            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @PostMapping("/notice")
    public ResponseEntity<?> postNoticeWithImage(@RequestPart("noticeDTO") NoticeDTO noticeDTO,
                                                 @RequestPart(value = "fileDTOList", required = false) List<FileDTO> noticeFileDTOList,
                                                 @PageableDefault(page = 0, size = 10) Pageable pageable) {
        ResponseDTO<NoticeDTO> responseDTO = new ResponseDTO<>();
        try {
            noticeDTO.setNoticeFileDTOList(noticeFileDTOList);

            noticeService.post(noticeDTO);
            temporaryImage.clear();
            // 페이지네이션된 모든 글 불러오기
            Page<NoticeDTO> noticeDTOPage = noticeService.searchAll(pageable, "all", "");

            responseDTO.setPageItems(noticeDTOPage);
            responseDTO.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
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
            NoticeDTO noticeDTO = noticeService.findById(noticeNo);
            String profileImageUrl = memberService.getProfileImageUrl(noticeDTO.getNoticeWriter());
            noticeDTO.setProfileImageUrl(profileImageUrl);

            responseDTO.setItem(noticeDTO);
            responseDTO.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            responseDTO.setErrorCode(404);
            responseDTO.setErrorMessage("Notice not found: " + e.getMessage());
            responseDTO.setStatusCode(HttpStatus.NOT_FOUND.value());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<?> upload(MultipartFile upload) {
        Map<String, String> result = new HashMap<>();

        FileDTO fileDTO = fileUtils.parseFileInfo(upload, "notice/");
        temporaryImage.add(fileDTO.getItemFilePath() + fileDTO.getItemFileName());
        System.out.println(temporaryImage);
        System.out.println("https://kr.object.ncloudstorage.com/bitcamp-bucket-36/" + fileDTO.getItemFilePath() + fileDTO.getItemFileName());
        result.put("url", "https://kr.object.ncloudstorage.com/bitcamp-bucket-36/" + fileDTO.getItemFilePath() + fileDTO.getItemFileName());
        result.put("itemFilePath", fileDTO.getItemFilePath());
        result.put("itemFileName", fileDTO.getItemFileName());
        result.put("itemFileOrigin", fileDTO.getItemFileOrigin());

        return ResponseEntity.ok(result);
    }
    @PutMapping("/remove")
    public ResponseEntity<?> handleNotSaveRequest() {
        try{
         noticeService.removeImage(temporaryImage);
         temporaryImage.clear();
         return ResponseEntity.ok().body("이미지가 삭제되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("이미지 삭제에 실패했습니다.");
        }
}
}

