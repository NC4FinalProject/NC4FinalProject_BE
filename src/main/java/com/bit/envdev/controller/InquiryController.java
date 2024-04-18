package com.bit.envdev.controller;

import com.bit.envdev.common.FileUtils;
import com.bit.envdev.constant.Role;
import com.bit.envdev.dto.*;
import com.bit.envdev.entity.CustomUserDetails;
import com.bit.envdev.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
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
@ToString

public class InquiryController {
    private final InquiryService inquiryService;
    private final FileUtils fileUtils;
    private final InquiryLikeService inquiryLikeService;
    private final InquiryCommentService inquiryCommentService;
    private final InquiryCommentLikeService inquiryCommentLikeService;
    private final PaymentService paymentService;
    private List<String> temporaryImage = new ArrayList<>();

    @GetMapping("/inquiry")
    public ResponseEntity<?> getInquiryList(@PageableDefault(page = 0, size = 5) Pageable pageable,
                                            @RequestParam("contentsId") int contentsId,
                                            @RequestParam("searchCondition") String searchCondition,
                                            @RequestParam("searchKeyword") String searchKeyword,
                                            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        String loginMemberNickname = null;
        long loginMemberId = 0;
        List<PaymentDTO> paymentDTOList = null;
        Role loginMemberRole = null;
        String contentsTitle = null;

        if (customUserDetails != null && customUserDetails.getMember() != null) {
            loginMemberNickname = customUserDetails.getMember().getUserNickname();
            loginMemberId = customUserDetails.getId();
            loginMemberRole = customUserDetails.getMember().getRole();
            paymentDTOList = paymentService.getPaymentList(loginMemberId);
        }

        try {
            Page<InquiryDTO> inquiryDTOPage = inquiryService.searchAll(pageable, searchCondition, searchKeyword, contentsId);

            Map<String, Object> responseData = new HashMap<>();
            responseData.put("inquiryList", inquiryDTOPage);
            responseData.put("search", (InquiryDTO.builder()
                    .searchCondition(searchCondition)
                    .searchKeyword(searchKeyword)
                    .build()));
            responseData.put("loginMemberId", loginMemberId);
            responseData.put("loginMemberNickname", loginMemberNickname);
            responseData.put("loginMemberRole", loginMemberRole);
            responseData.put("paymentList", paymentDTOList);
            responseData.put("contentsTitle", contentsTitle);

            return ResponseEntity.ok(responseData);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("errorCode", 401);
            errorResponse.put("errorMessage", e.getMessage());
            errorResponse.put("statusCode", HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @PostMapping("/inquiry/{contentsId}")
    public ResponseEntity<?> postInquiryWithImage(@PathVariable("contentsId") int contentsId,
                                                  @RequestPart("inquiryDTO") InquiryDTO inquiryDTO,
                                                  @RequestPart(value = "inquiryFileDTOList", required = false) List<InquiryFileDTO> inquiryFileDTOList,
                                                  @RequestPart(value = "tagDTOList", required = false) List<TagDTO> tagDTOList,
                                                  @RequestPart(value = "isPrivate", required = false) boolean isPrivate,
                                                  @PageableDefault(page = 0, size = 5) Pageable pageable,
                                                  @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        ResponseDTO<InquiryDTO> responseDTO = new ResponseDTO<>();
        try {
            inquiryDTO.setContentsId(contentsId);
            inquiryDTO.setPrivate(isPrivate);
            inquiryDTO.setInquiryFileDTOList(inquiryFileDTOList);
            inquiryDTO.setTagDTOList(tagDTOList);
            inquiryService.post(inquiryDTO, customUserDetails.getMember().getMemberId());
            temporaryImage.clear();
            Page<InquiryDTO> inquiryDTOPage = inquiryService.searchAll(pageable, "all", "", contentsId);

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
            result.put("commentLikeCnt", String.valueOf(inquiryCommentLikeCnt));
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            result.put("check", "error");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
    }

    @PostMapping("/commentlike/{inquiryCommentId}")
    public ResponseEntity<?> commentToggleLike(@PathVariable("inquiryCommentId") long inquiryCommentId, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        ResponseDTO<InquiryCommentDTO> responseDTO = new ResponseDTO();
        try {

            List<InquiryCommentDTO> commentDTOList = inquiryCommentLikeService.insertLike(customUserDetails.getMember(), inquiryCommentId);

            responseDTO.setItems(commentDTOList);
            responseDTO.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            responseDTO.setErrorCode(404);
            responseDTO.setErrorMessage("inquiry not found: " + e.getMessage());
            responseDTO.setStatusCode(HttpStatus.NOT_FOUND.value());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
        }
    }


    @GetMapping("/inquiry/{inquiryId}")
    public ResponseEntity<?> getInquiry(@PathVariable("inquiryId") Long inquiryId, HttpServletRequest request, HttpServletResponse response) {
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
    public ResponseEntity<?> delete(@PageableDefault(page = 0, size = 5) Pageable pageable,
                                    @PathVariable("inquiryId") long inquiryId,
                                    @RequestParam("contentsId") int contentsId,
                                    @RequestParam("searchCondition") String searchCondition,
                                    @RequestParam("searchKeyword") String searchKeyword,
                                    @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        ResponseDTO<InquiryDTO> responseDTO = new ResponseDTO<>();
        try {
            inquiryService.deleteById(inquiryId);

            Page<InquiryDTO> inquiryDTOPage = inquiryService.searchAll(pageable, searchCondition, searchKeyword, contentsId);

            responseDTO.setPageItems(inquiryDTOPage);
            responseDTO.setStatusCode(HttpStatus.OK.value());


            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            responseDTO.setErrorCode(404);
            responseDTO.setErrorMessage("inquiry not found: " + e.getMessage());
            responseDTO.setStatusCode(HttpStatus.NOT_FOUND.value());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
        }
    }

    @PutMapping("/update/{contentsId}")
    public ResponseEntity<?> updateInquiryInfo(@PathVariable("contentsId") int contentsId,
                                               @RequestPart("inquiryDTO") InquiryDTO inquiryDTO,
                                               @RequestPart(value = "inquiryFileDTOList", required = false) List<InquiryFileDTO> inquiryFileDTOList,
                                               @RequestPart(value = "tagDTOList", required = false) List<TagDTO> tagDTOList,
                                               @RequestPart(value = "isPrivate", required = false) boolean isPrivate,
                                               @PageableDefault(page = 0, size = 5) Pageable pageable,
                                               @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        ResponseDTO<InquiryDTO> responseDTO = new ResponseDTO<>();
        try {
            inquiryDTO.setContentsId(contentsId);
            inquiryDTO.setPrivate(isPrivate);
            inquiryDTO.setInquiryFileDTOList(inquiryFileDTOList);
            inquiryDTO.setTagDTOList(tagDTOList);

            InquiryDTO updatedInquiryDTO = inquiryService.modify(inquiryDTO, customUserDetails.getMember().getMemberId());

            temporaryImage.clear();

            updatedInquiryDTO.setContentsTitle(inquiryService.getContentsTitle(updatedInquiryDTO.getContentsId()));
            updatedInquiryDTO.setAuthor(inquiryService.getContentsAuthor(updatedInquiryDTO.getContentsId()));
            updatedInquiryDTO.setLikeCount(inquiryService.getLikeCount(updatedInquiryDTO.getInquiryId()));
            long memberLike = inquiryLikeService.findByMemberIdAndInquiryId(customUserDetails.getMember().getMemberId(), inquiryDTO.getInquiryId());

            if(memberLike == 0) {
                updatedInquiryDTO.setLike(false);
            } else {
                updatedInquiryDTO.setLike(true);
            }

            responseDTO.setItem(updatedInquiryDTO);

            responseDTO.setStatusCode(HttpStatus.OK.value());
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            responseDTO.setErrorCode(404);
            responseDTO.setErrorMessage("inquiry not found: " + e.getMessage());
            responseDTO.setStatusCode(HttpStatus.NOT_FOUND.value());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
        }
    }

//    @PutMapping("/updateProc")
//    public ResponseEntity<?> updateInquiry(MultipartFile upload, @RequestPart("inquiryDTO") InquiryDTO inquiryDTO,
//                                           @RequestPart("modifyFiles") List<Long> modifyInquiryFiles,
//                                           @RequestPart(value = "fileDTOList", required = false) List<InquiryFileDTO> inquiryFileDTOList) {
//        ResponseDTO<InquiryDTO> responseDTO = new ResponseDTO<>();
//        try {
//            inquiryDTO.setInquiryFileDTOList(inquiryFileDTOList);
//            inquiryService.modifyInquiryFile(modifyInquiryFiles);
//            inquiryService.modify(inquiryDTO);
//            responseDTO.setStatusCode(HttpStatus.OK.value());
//
//            return ResponseEntity.ok(responseDTO);
//        } catch (Exception e) {
//            responseDTO.setErrorCode(404);
//            responseDTO.setErrorMessage("inquiry not found: " + e.getMessage());
//            responseDTO.setStatusCode(HttpStatus.NOT_FOUND.value());
//
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
//        }
//    }

    @PostMapping("/upload")
    public ResponseEntity<?> upload(MultipartFile upload) {
        Map<String, String> result = new HashMap<>();

        System.out.println(upload.getOriginalFilename());

        InquiryFileDTO inquiryFileDTO = fileUtils.parseInquiryFileInfo(upload, "inquiry/");
        temporaryImage.add(inquiryFileDTO.getInquiryFilePath() + inquiryFileDTO.getInquiryFileName());
        System.out.println(temporaryImage);
        System.out.println("https://kr.object.ncloudstorage.com/envdev/" + inquiryFileDTO.getInquiryFilePath() + inquiryFileDTO.getInquiryFileName());
        result.put("url", "https://kr.object.ncloudstorage.com/envdev/" + inquiryFileDTO.getInquiryFilePath() + inquiryFileDTO.getInquiryFileName());
        result.put("inquiryFilePath", inquiryFileDTO.getInquiryFilePath());
        result.put("inquiryFileName", inquiryFileDTO.getInquiryFileName());
        result.put("inquiryFileOrigin", inquiryFileDTO.getInquiryFileOrigin());

        return ResponseEntity.ok(result);
    }

    @PutMapping("/remove")
    public ResponseEntity<?> handleNotSaveRequest() {
        try {
            inquiryService.removeImage(temporaryImage);
            temporaryImage.clear();
            return ResponseEntity.ok().body("이미지가 삭제되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("이미지 삭제에 실패했습니다.");
        }
    }

    @PostMapping("/comment")
    public ResponseEntity<?> postComment(@RequestBody InquiryCommentDTO inquiryCommentDTO,
                                         @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        ResponseDTO<InquiryCommentDTO> responseDTO = new ResponseDTO<>();
        try {
            List<InquiryCommentDTO> commentDTOList = inquiryCommentService.post(inquiryCommentDTO, customUserDetails);

            responseDTO.setItems(commentDTOList);
            responseDTO.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            responseDTO.setErrorCode(404);
            responseDTO.setErrorMessage("inquiry not found: " + e.getMessage());
            responseDTO.setStatusCode(HttpStatus.NOT_FOUND.value());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
        }
    }

    @PutMapping("/comment")
    public ResponseEntity<?> modifyComment(@RequestBody InquiryCommentDTO inquiryCommentDTO, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        ResponseDTO<InquiryCommentDTO> responseDTO = new ResponseDTO<>();
        try {
            List<InquiryCommentDTO> inquiryCommentDTOList = inquiryCommentService.modify(inquiryCommentDTO, customUserDetails);

            responseDTO.setItems(inquiryCommentDTOList);
            responseDTO.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            responseDTO.setErrorCode(404);
            responseDTO.setErrorMessage("inquiry not found: " + e.getMessage());
            responseDTO.setStatusCode(HttpStatus.NOT_FOUND.value());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
        }
    }

    @DeleteMapping("/comment/{inquiryCommentId}")
    public ResponseEntity<?> deleteComment(@RequestParam Long inquiryId, @PathVariable("inquiryCommentId") Long inquiryCommentId, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        ResponseDTO<InquiryCommentDTO> responseDTO = new ResponseDTO<>();
        try {
            List<InquiryCommentDTO> inquiryCommentDTOList = inquiryCommentService.delete(inquiryId, inquiryCommentId, customUserDetails);

            responseDTO.setItems(inquiryCommentDTOList);
            responseDTO.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            responseDTO.setErrorCode(404);
            responseDTO.setErrorMessage("inquiry not found: " + e.getMessage());
            responseDTO.setStatusCode(HttpStatus.NOT_FOUND.value());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
        }
    }

    @GetMapping("/updateInquiryView/{inquiryId}")
    public ResponseEntity<?> updateInquiryView(@PathVariable("inquiryId") Long inquiryId, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        ResponseDTO<InquiryDTO> responseDTO = new ResponseDTO<>();
        try {
            InquiryDTO updatedInquiryDTO = inquiryService.updateInquiryView(inquiryId);

            updatedInquiryDTO.setContentsTitle(inquiryService.getContentsTitle(updatedInquiryDTO.getContentsId()));
            updatedInquiryDTO.setAuthor(inquiryService.getContentsAuthor(updatedInquiryDTO.getContentsId()));
            updatedInquiryDTO.setLikeCount(inquiryService.getLikeCount(updatedInquiryDTO.getInquiryId()));


            long memberLike = inquiryLikeService.findByMemberIdAndInquiryId(customUserDetails.getMember().getMemberId(), updatedInquiryDTO.getInquiryId());

            if(memberLike == 0) {
                updatedInquiryDTO.setLike(false);
            } else {
                updatedInquiryDTO.setLike(true);
            }

            responseDTO.setItem(updatedInquiryDTO);
            responseDTO.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            responseDTO.setErrorCode(404);
            responseDTO.setErrorMessage("inquiry not found: " + e.getMessage());
            responseDTO.setStatusCode(HttpStatus.NOT_FOUND.value());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
        }
    }

    @GetMapping("/myInquiries/{contentsId}")
    public ResponseEntity<?> myInquiries(@PageableDefault(page = 0, size = 5) Pageable pageable,
                                         @PathVariable("contentsId") int contentsId,
                                         @RequestParam("searchCondition") String searchCondition,
                                         @RequestParam("searchKeyword") String searchKeyword,
                                         @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        ResponseDTO<InquiryDTO> responseDTO = new ResponseDTO<>();
        try {
            Page<InquiryDTO> inquiryDTOPage = inquiryService.myInquiries(pageable, searchCondition, searchKeyword, contentsId, customUserDetails.getMember().getMemberId());

            responseDTO.setPageItems(inquiryDTOPage);

            responseDTO.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            responseDTO.setErrorCode(404);
            responseDTO.setErrorMessage("inquiry not found: " + e.getMessage());
            responseDTO.setStatusCode(HttpStatus.NOT_FOUND.value());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
        }
    }

    @PutMapping("/updateSolve/{inquiryId}")
    public ResponseEntity<?> updateSolve(@PathVariable("inquiryId") long inquiryId,
                                         @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        ResponseDTO<InquiryDTO> responseDTO = new ResponseDTO<>();
        try {
            InquiryDTO updatedInquiryDTO = inquiryService.upadateSolve(inquiryId);

            updatedInquiryDTO.setContentsTitle(inquiryService.getContentsTitle(updatedInquiryDTO.getContentsId()));
            updatedInquiryDTO.setAuthor(inquiryService.getContentsAuthor(updatedInquiryDTO.getContentsId()));
            updatedInquiryDTO.setLikeCount(inquiryService.getLikeCount(updatedInquiryDTO.getInquiryId()));
            long memberLike = inquiryLikeService.findByMemberIdAndInquiryId(customUserDetails.getMember().getMemberId(), updatedInquiryDTO.getInquiryId());
            if(memberLike == 0) {
                updatedInquiryDTO.setLike(false);
            } else {
                updatedInquiryDTO.setLike(true);
            }

            responseDTO.setItem(updatedInquiryDTO);

            responseDTO.setStatusCode(HttpStatus.OK.value());
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            responseDTO.setErrorCode(404);
            responseDTO.setErrorMessage("inquiry not found: " + e.getMessage());
            responseDTO.setStatusCode(HttpStatus.NOT_FOUND.value());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
        }
    }

    @PostMapping("/like/{inquiryId}")
    public ResponseEntity<?> like(@PathVariable("inquiryId") long inquiryId,
                                  @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        ResponseDTO<InquiryDTO> responseDTO = new ResponseDTO<>();
        try {
            long likeCnt = inquiryLikeService.findByMemberIdAndInquiryId(customUserDetails.getMember().getMemberId(), inquiryId);

            InquiryDTO inquiryDTO = new InquiryDTO();

            if(likeCnt == 0) {
                inquiryDTO = inquiryLikeService.insertLike(customUserDetails.getMember(), inquiryId);
                inquiryDTO.setLike(true);
            } else {
                inquiryDTO = inquiryLikeService.deleteLike(customUserDetails.getMember(), inquiryId);
                inquiryDTO.setLike(false);
            }

            inquiryDTO.setContentsTitle(inquiryService.getContentsTitle(inquiryDTO.getContentsId()));
            inquiryDTO.setAuthor(inquiryService.getContentsAuthor(inquiryDTO.getContentsId()));
            inquiryDTO.setLikeCount(inquiryService.getLikeCount(inquiryDTO.getInquiryId()));

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

    @GetMapping("/comments/{inquiryId}")
    public ResponseEntity<?> getComments(@PathVariable("inquiryId") Long inquiryId,
                                         @RequestParam("order") String order,
                                         @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        ResponseDTO<InquiryCommentDTO> responseDTO = new ResponseDTO<>();
        try {
            List<InquiryCommentDTO> inquiryCommentDTOList = inquiryCommentService.getComments(inquiryId, order, customUserDetails);

            responseDTO.setItems(inquiryCommentDTOList);
            responseDTO.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            responseDTO.setErrorCode(404);
            responseDTO.setErrorMessage("inquiry not found: " + e.getMessage());
            responseDTO.setStatusCode(HttpStatus.NOT_FOUND.value());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
        }
    }
}
