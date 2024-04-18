package com.bit.envdev.controller;

import com.bit.envdev.common.FileUtils;
import com.bit.envdev.dto.FileDTO;
import com.bit.envdev.dto.MemberDTO;
import com.bit.envdev.dto.QnaDTO;
import com.bit.envdev.dto.ResponseDTO;
import com.bit.envdev.entity.CustomUserDetails;
import com.bit.envdev.service.MemberService;
import com.bit.envdev.service.PaymentService;
import com.bit.envdev.service.QnaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MypageController {
    private final MemberService memberService;
    private final FileUtils fileUtils;
    private final QnaService qnaService;
    private final PaymentService paymentService;

    @GetMapping
    public ResponseEntity<?> mypage(@AuthenticationPrincipal UserDetails userDetails) {
        ResponseDTO<MemberDTO> responseDTO = new ResponseDTO<>();

        String username = userDetails.getUsername();
        MemberDTO memberDTO = memberService.findByUsername(username);

        try {
            Map<String, String> msgMap = new HashMap<>();

            msgMap.put("msg", "마이페이지 입니다.");
            responseDTO.setItem(memberDTO);
            responseDTO.setStatusCode(HttpStatus.OK.value());
            return ResponseEntity.ok(responseDTO);

        } catch (Exception e) {
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setErrorCode(202);
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());

            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @PostMapping("/profile-file")
    public ResponseEntity<?> profileFile(@AuthenticationPrincipal UserDetails userDetails,
                                         @RequestPart(value = "profile_image") MultipartFile profileImage) {

        ResponseDTO<Map<String, String>> responseDTO = new ResponseDTO<>();
        String fileString = null;

        try {
            if (profileImage.getOriginalFilename() != null &&
                    !profileImage.getOriginalFilename().isEmpty()) {
                FileDTO fileDTO = fileUtils.parseFileInfo(profileImage, "profile/");
                fileString = (fileDTO.getItemFilePath() + fileDTO.getItemFileName());
            }
            String username = userDetails.getUsername();
            MemberDTO memberDTO = memberService.findByUsername(username);

            memberService.updateProfile(fileString, memberDTO);
            Map<String, String> msgMap = new HashMap<>();

            msgMap.put("msg", "정상적으로 입력되었습니다.");

            responseDTO.setItem(msgMap);
            responseDTO.setStatusCode(HttpStatus.OK.value());
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setErrorCode(202);
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @PostMapping("/user-nickname")
    public ResponseEntity<?> profileFile(@AuthenticationPrincipal UserDetails userDetails,
                                        @RequestPart(value = "user_nickname") String userNickname) {


        ResponseDTO<Map<String, String>> responseDTO = new ResponseDTO<>();

        try {
            String username = userDetails.getUsername();
            MemberDTO newMemberDTO = memberService.findByUsername(username);

            memberService.updateUserNickname(userNickname, newMemberDTO);
            Map<String, String> msgMap = new HashMap<>();

            msgMap.put("msg", "정상적으로 입력되었습니다.");

            responseDTO.setItem(msgMap);
            responseDTO.setStatusCode(HttpStatus.OK.value());
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setErrorCode(202);
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @GetMapping("/wannabe-teacher")
    public ResponseEntity<?> wannabeTeacher(@AuthenticationPrincipal UserDetails userDetails) {

        ResponseDTO<Map<String, String>> responseDTO = new ResponseDTO<>();

        try {
            String username = userDetails.getUsername();
            MemberDTO memberDTO = memberService.findByUsername(username);

            memberService.wannabeTeacher(memberDTO);
            Map<String, String> msgMap = new HashMap<>();

            msgMap.put("msg", "정상적으로 입력되었습니다.");

            responseDTO.setItem(msgMap);
            responseDTO.setStatusCode(HttpStatus.OK.value());
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setErrorCode(202);
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @GetMapping("/qna")
    public ResponseEntity<?> getmyqna(@PageableDefault(page = 0, size = 15) Pageable pageable,
                                                               @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        ResponseDTO<QnaDTO> responseDTO = new ResponseDTO<>();
        try {
            Page<QnaDTO> qnaDTOList = qnaService.getMyQnaData(pageable, customUserDetails.getMember());
            responseDTO.setPageItems(qnaDTOList);
            responseDTO.setStatusCode(HttpStatus.OK.value());
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setErrorCode(205);
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @GetMapping("/purchaselist")
    public ResponseEntity<?> getPurchaseList(@PageableDefault(page = 0, size = 15) Pageable pageable,
                                            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        ResponseDTO<Map<String, Object>> responseDTO = new ResponseDTO<>();
        try {
            Page<Map<String, Object>> purchaseList = paymentService.getPurchaseList(pageable, customUserDetails.getMember());
            responseDTO.setPageItems(purchaseList);
            responseDTO.setStatusCode(HttpStatus.OK.value());
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setErrorCode(205);
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }
}