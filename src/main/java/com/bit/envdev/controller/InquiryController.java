package com.bit.envdev.controller;

import com.bit.envdev.common.FileUtils;
import com.bit.envdev.dto.*;
import com.bit.envdev.entity.CustomUserDetails;
import com.bit.envdev.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/inquiry")

public class InquiryController {
    private final InquiryService inquiryService;
    private final FileUtils fileUtils;
    private final InquiryLikeService inquiryLikeService;
    private final InquiryCommentService inquiryCommentService;
    private final InquiryCommentLikeService inquiryCommentLikeService;
    private final TagService tagService;
    private List<String> temporaryImage = new ArrayList<>();

    @GetMapping("/inquiry")
    public ResponseEntity<?> getInquiryList(@PageableDefault(page = 0, size = 5) Pageable pageable,
                                          @RequestParam("searchCondition") String searchCondition,
                                          @RequestParam("searchKeyword") String searchKeyword,
                                          @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        ResponseDTO<InquiryDTO> responseDTO = new ResponseDTO<>();

        try {
            Page<InquiryDTO> inquiryDTOPage = inquiryService.searchAll(pageable, searchCondition, searchKeyword);

            responseDTO.setPageItems(inquiryDTOPage);
            responseDTO.setItem(InquiryDTO.builder()
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

    @PostMapping("/inquiry")
    public ResponseEntity<?> postInquiryWithImage(@RequestPart("inquiryDTO") InquiryDTO inquiryDTO,
                                                 @RequestPart(value = "inquiryFileDTOList", required = false) List<InquiryFileDTO> inquiryFileDTOList,
                                                 @PageableDefault(page = 0, size = 5) Pageable pageable) {
        ResponseDTO<InquiryDTO> responseDTO = new ResponseDTO<>();
        try {
            inquiryDTO.setInquiryFileDTOList(inquiryFileDTOList);

            inquiryService.post(inquiryDTO);
            temporaryImage.clear();
            Page<InquiryDTO> inquiryDTOPage = inquiryService.searchAll(pageable, "all", "");

            responseDTO.setPageItems(inquiryDTOPage);
            responseDTO.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            responseDTO.setErrorCode(500);
            responseDTO.setErrorMessage("Internal server error: " + e.getMessage());
            responseDTO.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
        }
    }


    @GetMapping("/likeget/{inquiryId}")
    public ResponseEntity<?> getLikeCnt(@PathVariable("inquiryId") long inquiryId, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Map<String, String> result = new HashMap<>();
        try {
            long inquiryLikeCnt = inquiryLikeService.findByInquiryId(inquiryId);
            long inquiryCnt = inquiryLikeService.addOrdown(customUserDetails.getMember().getMemberId(), inquiryId);
            result.put("check", String.valueOf(inquiryCnt));
            result.put("likeCnt", String.valueOf(inquiryLikeCnt));
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            result.put("check", "error");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
    }

    @GetMapping("/commentlikeget/{inquiryCommentId}")
    public ResponseEntity<?> getCommentLikeCnt(@PathVariable("inquiryCommentId") long inquiryCommentId, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Map<String, String> result = new HashMap<>();
        try {
            long inquiryCommentLikeCnt = inquiryCommentLikeService.findByInquiryCommentId(inquiryCommentId);
            long inquiryCommentCnt = inquiryCommentLikeService.addOrdown(customUserDetails.getMember().getMemberId(), inquiryCommentId);
            result.put("check", String.valueOf(inquiryCommentCnt));
            result.put("likeCnt", String.valueOf(inquiryCommentLikeCnt));
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            result.put("check", "error");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
    }

    @PostMapping("/like/{inquiryId}")
    public ResponseEntity<?> toggleLike(@PathVariable("inquiryId") long inquiryId, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Map<String, String> result = new HashMap<>();
        try {

            inquiryLikeService.insertLike(customUserDetails.getMember(), inquiryId);
            long inquiryCnt = inquiryLikeService.addOrdown(customUserDetails.getMember().getMemberId(), inquiryId);
            long inquiryLikeCnt = inquiryLikeService.findByInquiryId(inquiryId);

            result.put("check", String.valueOf(inquiryCnt));
            result.put("likeCnt", String.valueOf(inquiryLikeCnt));
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            result.put("check", "error");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
    }

    @PostMapping("/commentlike/{inquiryCommentId}")
    public ResponseEntity<?> commentToggleLike(@PathVariable("inquiryCommentId") long inquiryCommentId, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Map<String, String> result = new HashMap<>();
        try {

            inquiryLikeService.insertLike(customUserDetails.getMember(), inquiryCommentId);
            // T/F 판단 눌렀는지
            long inquiryCnt = inquiryLikeService.addOrdown(customUserDetails.getMember().getMemberId(), inquiryCommentId);
            long inquiryLikeCnt = inquiryLikeService.findByInquiryId(inquiryCommentId);

            result.put("check", String.valueOf(inquiryCommentId));
            result.put("likeCnt", String.valueOf(inquiryCommentId));
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            result.put("check", "error");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
    }


    @GetMapping("/inquriy/{inquriyId}")
    public ResponseEntity<?> getInquiry(@PathVariable("inquriyId") Long inquiryId, HttpServletRequest request, HttpServletResponse response) {
        ResponseDTO<InquiryDTO> responseDTO = new ResponseDTO<>();
        try {
            HttpSession session = request.getSession();

            InquiryDTO inquiryDTO = inquiryService.findById(inquiryId);
            inquiryService.updateView(inquiryId, request, response);

            responseDTO.setItem(inquiryDTO);
            responseDTO.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            responseDTO.setErrorCode(404);
            responseDTO.setErrorMessage("inquiry not found: " + e.getMessage());
            responseDTO.setStatusCode(HttpStatus.NOT_FOUND.value());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
        }
    }

    @DeleteMapping("/delete/{inquiryId}")
    public  ResponseEntity<?> delete(@PathVariable("inquiryId") Long inquiryId) {
        ResponseDTO<InquiryDTO> responseDTO = new ResponseDTO<>();
        try {
            inquiryService.deleteById(inquiryId);
            responseDTO.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            responseDTO.setErrorCode(404);
            responseDTO.setErrorMessage("inquiry not found: " + e.getMessage());
            responseDTO.setStatusCode(HttpStatus.NOT_FOUND.value());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateInquiryInfo(MultipartFile upload, @RequestPart("inquiryDTO") InquiryDTO inquiryDTO,
                                              @RequestPart(value = "inquiryFileDTOList", required = false) List<InquiryFileDTO> inquiryFileDTOList) {
        ResponseDTO<List<Long>> responseDTO = new ResponseDTO<>();
        try {
            List<Long> modifyInquiryFileLIst = inquiryService.modifyInquiryFileList(inquiryDTO);
            inquiryService.modifyInquiryFile(modifyInquiryFileLIst);
            temporaryImage.clear();

            responseDTO.setStatusCode(HttpStatus.OK.value());
            responseDTO.setItem(modifyInquiryFileLIst);
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            responseDTO.setErrorCode(404);
            responseDTO.setErrorMessage("inquiry not found: " + e.getMessage());
            responseDTO.setStatusCode(HttpStatus.NOT_FOUND.value());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
        }
    }

    @PutMapping("/updateProc")
    public ResponseEntity<?> updateInquiry(MultipartFile upload, @RequestPart("inquiryDTO") InquiryDTO inquiryDTO,
                                          @RequestPart("modifyFiles") List<Long> modifyInquiryFiles,
                                          @RequestPart(value = "fileDTOList", required = false) List<InquiryFileDTO> inquiryFileDTOList) {
        ResponseDTO<InquiryDTO> responseDTO = new ResponseDTO<>();
        try {
            inquiryDTO.setInquiryFileDTOList(inquiryFileDTOList);
            inquiryService.modifyInquiryFile(modifyInquiryFiles);
            inquiryService.modify(inquiryDTO);
            responseDTO.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            responseDTO.setErrorCode(404);
            responseDTO.setErrorMessage("inquiry not found: " + e.getMessage());
            responseDTO.setStatusCode(HttpStatus.NOT_FOUND.value());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<?> upload(MultipartFile upload) {
        Map<String, String> result = new HashMap<>();

        InquiryFileDTO inquiryFileDTO = fileUtils.parseInquiryFileInfo(upload, "inquiry/");
        temporaryImage.add(inquiryFileDTO.getInquiryFilePath() + inquiryFileDTO.getInquiryFileName());
        System.out.println(temporaryImage);
        System.out.println("https://kr.object.ncloudstorage.com/bitcamp-bucket-36/" + inquiryFileDTO.getInquiryFilePath() + inquiryFileDTO.getInquiryFileName());
        result.put("url", "https://kr.object.ncloudstorage.com/bitcamp-bucket-36/" + inquiryFileDTO.getInquiryFilePath() + inquiryFileDTO.getInquiryFileName());
        result.put("inquiryFilePath", inquiryFileDTO.getInquiryFilePath());
        result.put("inquiryFileName", inquiryFileDTO.getInquiryFileName());
        result.put("inquiryFileOrigin", inquiryFileDTO.getInquiryFileOrigin());

        return ResponseEntity.ok(result);
    }
    @PutMapping("/remove")
    public ResponseEntity<?> handleNotSaveRequest() {
        try{
            inquiryService.removeImage(temporaryImage);
            temporaryImage.clear();
            return ResponseEntity.ok().body("이미지가 삭제되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("이미지 삭제에 실패했습니다.");
        }
    }

}
