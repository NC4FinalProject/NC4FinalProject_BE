package com.bit.envdev.controller;

import com.bit.envdev.dto.EmailVerifyMemberDTO;
import com.bit.envdev.dto.MemberDTO;
import com.bit.envdev.dto.ResponseDTO;
import com.bit.envdev.entity.Member;
import com.bit.envdev.service.impl.MemberServiceImpl;
import com.bit.envdev.service.impl.PointServiceImpl;
import com.bit.envdev.service.impl.SendEmailServiceImpl;

import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    
    private final MemberServiceImpl memberService;
    private final SendEmailServiceImpl sendEmailService;
    private final PointServiceImpl pointService;

    private static String tempCode = "";

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody MemberDTO memberDTO) {
        ResponseDTO<MemberDTO> responseDTO = new ResponseDTO<>();

        try {
            Member newMember = memberService.join(memberDTO);
            pointService.pointJoinWithBuilder(newMember, 3000,  "회원가입 축하 포인트 지급");

            responseDTO.setItem(newMember.toDTO());
            responseDTO.setStatusCode(HttpStatus.OK.value());
            return ResponseEntity.ok(responseDTO);
            
        } catch(Exception e) {
            if(e.getMessage().equalsIgnoreCase("Invalid Argument")) {
                responseDTO.setErrorCode(100);
                responseDTO.setErrorMessage(e.getMessage());
            } else if(e.getMessage().equalsIgnoreCase("already exist username")) {
                responseDTO.setErrorCode(101);
                responseDTO.setErrorMessage(e.getMessage());
            } else {
                responseDTO.setErrorCode(102);
                responseDTO.setErrorMessage(e.getMessage());
            }

            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberDTO memberDTO) {
        ResponseDTO<MemberDTO> responseDTO = new ResponseDTO<>();

        try {
            MemberDTO loginMemberDTO = memberService.login(memberDTO);

            loginMemberDTO.setPassword("");

            responseDTO.setItem(loginMemberDTO);
            responseDTO.setStatusCode(HttpStatus.OK.value());
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if(e.getMessage().equalsIgnoreCase("not exist username")) {
                responseDTO.setErrorCode(200);
                responseDTO.setErrorMessage(e.getMessage());
            } else if(e.getMessage().equalsIgnoreCase("wrong password")) {
                responseDTO.setErrorCode(201);
                responseDTO.setErrorMessage(e.getMessage());
            } else if(e.getMessage().equalsIgnoreCase("탈퇴한 유저입니다.")){
                responseDTO.setErrorCode(202);
                responseDTO.setErrorMessage(e.getMessage());
            } else {
                responseDTO.setErrorCode(203);
                responseDTO.setErrorMessage(e.getMessage());
            }

            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @DeleteMapping("/resign")
    public ResponseEntity<?> resign(@AuthenticationPrincipal UserDetails userDetails) {
        ResponseDTO<MemberDTO> responseDTO = new ResponseDTO<>();
        String username = userDetails.getUsername();

        try {
            memberService.resign(username);

            responseDTO.setStatusCode(HttpStatus.OK.value());
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            responseDTO.setErrorCode(202);
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

//    @PostMapping("/email-verification")
//    public ResponseEntity<?> emailVerification(EmailVerifyMemberDTO verifyMemberDTO) {
//        ResponseDTO<MemberDTO> responseDTO = new ResponseDTO<>();
//
//        try {
//            String username = verifyMemberDTO.getUsername();
//            MemberDTO memberDTO = memberService.findByUsername(username);
//
//                if ("verified".equals(memberDTO.getEmailVerification())) {
//                    responseDTO.setErrorCode(201);
//                    responseDTO.setErrorMessage("이미 인증된 이메일 입니다.");
//                    responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
//                    return ResponseEntity.badRequest().body(responseDTO);
//                } else {
//                    sendEmailService.createMail(memberDTO);
//                    responseDTO.setItem(memberDTO);
//                    responseDTO.setStatusCode(HttpStatus.OK.value());
//                    return ResponseEntity.ok(responseDTO);
//                }
//        } catch (Exception e) {
//            responseDTO.setErrorCode(200);
//            responseDTO.setErrorMessage(e.getMessage());
//            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
//            return ResponseEntity.badRequest().body(responseDTO);
//        }
//    }

    @Transactional
    @PostMapping("/email-verification")
    public ResponseEntity<?> emailVerification(@RequestBody EmailVerifyMemberDTO verifyMemberDTO) {
        ResponseDTO<MemberDTO> responseDTO = new ResponseDTO<>();
        try {
            String username = verifyMemberDTO.getUsername();
            MemberDTO memberDTO = memberService.findByUsername(username);
            if ("verified".equals(memberDTO.getEmailVerification())) {
                responseDTO.setErrorCode(201);
                responseDTO.setErrorMessage("이미 인증된 이메일 입니다.");
                responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
                return ResponseEntity.badRequest().body(responseDTO);
            } else {
                tempCode = sendEmailService.createMail(verifyMemberDTO);
//                responseDTO.setItem(memberDTO);
//                responseDTO.setStatusCode(HttpStatus.OK.value());
                return ResponseEntity.ok(responseDTO);
            }
        } catch (Exception e) {
            responseDTO.setErrorCode(200);
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @PostMapping("/email-check")
    public ResponseEntity<?> emailCheck(@RequestBody MemberDTO memberDTO) {
        ResponseDTO<MemberDTO> responseDTO = new ResponseDTO<>();

        try {
            MemberDTO CheckMemberDTO = memberService.emailCheck(memberDTO);
            responseDTO.setItem(CheckMemberDTO);
            responseDTO.setStatusCode(HttpStatus.OK.value());
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            if(e.getMessage().equalsIgnoreCase("Invalid Argument")) {
                responseDTO.setErrorCode(200);
                responseDTO.setErrorMessage(e.getMessage());
            } else if(e.getMessage().equalsIgnoreCase("already exist username")) {
                responseDTO.setErrorCode(201);
                responseDTO.setErrorMessage(e.getMessage());
            } else {
                responseDTO.setErrorCode(202);
                responseDTO.setErrorMessage(e.getMessage());
            }

            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @Transactional
    @PostMapping("/code-check")
    public ResponseEntity<?> emailCheck(@RequestBody Object codeObject) {
        ResponseDTO<String> responseDTO = new ResponseDTO<>();

        try {
            Map<String, Object> map = (Map<String, Object>) codeObject;
            String value = map.get("code").toString();

            String checkResult = memberService.codeVerification(tempCode, value);

            if(checkResult.equals("correct")) {
                responseDTO.setItem("correct");
                responseDTO.setStatusCode(HttpStatus.OK.value());

                return ResponseEntity.ok(responseDTO);
            } else {
                responseDTO.setErrorCode(200);
                responseDTO.setErrorMessage("code is not correct");
                responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
                return ResponseEntity.badRequest().body(responseDTO);
            }

        } catch (Exception e) {
            responseDTO.setErrorCode(201);
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @PostMapping("/nickname-check")
    public ResponseEntity<?> nicknameCheck(@RequestBody MemberDTO memberDTO) {
        ResponseDTO<MemberDTO> responseDTO = new ResponseDTO<>();

        try {
            MemberDTO CheckMemberDTO = memberService.nicknameCheck(memberDTO);

            responseDTO.setItem(CheckMemberDTO);
            responseDTO.setStatusCode(HttpStatus.OK.value());
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            if(e.getMessage().equalsIgnoreCase("Invalid Argument")) {
                responseDTO.setErrorCode(200);
                responseDTO.setErrorMessage(e.getMessage());
            } else if(e.getMessage().equalsIgnoreCase("already exist userNickname")) {
                responseDTO.setErrorCode(201);
                responseDTO.setErrorMessage(e.getMessage());
            } else {
                responseDTO.setErrorCode(202);
                responseDTO.setErrorMessage(e.getMessage());
            }

            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout() {
        ResponseDTO<Map<String, String>> responseDTO = new ResponseDTO<>();

        try {
            SecurityContextHolder.clearContext();

            Map<String, String> msgMap = new HashMap<>();
            msgMap.put("logoutMsg", "logout success");

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
