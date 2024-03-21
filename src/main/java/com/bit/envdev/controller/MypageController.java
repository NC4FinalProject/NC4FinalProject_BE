package com.bit.envdev.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bit.envdev.common.FileUtils;
import com.bit.envdev.dto.FileDTO;
import com.bit.envdev.dto.MemberDTO;
import com.bit.envdev.dto.ResponseDTO;
import com.bit.envdev.entity.CustomUserDetails;
import com.bit.envdev.service.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MypageController {
    private final MemberService memberService;
    private final FileUtils fileUtils;

    @GetMapping
    public ResponseEntity<?> mypage(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        ResponseDTO<MemberDTO> responseDTO = new ResponseDTO<>();
        System.out.println("마이페이지");
        System.out.println(customUserDetails.getUsername());
        
        String username = customUserDetails.getUsername();
        MemberDTO memberDTO = memberService.findByUsername(username);

        System.out.println("username" + username);
        System.out.println("memberDTO.getProfileFile()" + memberDTO.getProfileFile());

        try {
            Map<String, String> msgMap = new HashMap<>();

            msgMap.put("msg", "마이페이지 입니다.");

            responseDTO.setItem(memberDTO);
            responseDTO.setStatusCode(HttpStatus.OK.value());
            return ResponseEntity.ok(responseDTO);

        } catch(Exception e) {
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setErrorCode(202);
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());

            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @PostMapping("/profile-file")
    public ResponseEntity<?> profileFile(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                         @RequestPart(value = "profile_image") MultipartFile profileImage) {

        ResponseDTO<Map<String, String>> responseDTO = new ResponseDTO<>();
        String fileString = null;

        try {
            if (profileImage.getOriginalFilename() != null &&
                    !profileImage.getOriginalFilename().isEmpty()) {
                        FileDTO fileDTO = fileUtils.parseFileInfo(profileImage, "profile/");
                        fileString = (fileDTO.getItemFilePath()+fileDTO.getItemFileName());
                        System.out.println("fileDTO.getItemFilePath()" + fileDTO.getItemFilePath());
                        System.out.println("fileDTO.getItemFileName()" + fileDTO.getItemFileName());
            }

            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            UserDetails userDetails = (UserDetails)principal;
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
}