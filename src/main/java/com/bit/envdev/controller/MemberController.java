package com.bit.envdev.controller;


import com.bit.envdev.dto.MemberDTO;
import com.bit.envdev.dto.PointDTO;
import com.bit.envdev.dto.PointHistoryDTO;
import com.bit.envdev.dto.ResponseDTO;
import com.bit.envdev.service.MemberService;
import com.bit.envdev.service.PointHistoryService;
import com.bit.envdev.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final PointService pointService;
    private final PointHistoryService pointHistoryService;
    private final PasswordEncoder passwordEncoder;

    // 기존 form submit이나 ajax에서는 전송하는 데이터 타입이 x-www-form-urlencoded 형식이어서
    // @ModelAttribute나 @RequestParam으로 데이터를 받을 수 있었다
    // axios나 fetch에서는 전송하는 데이터 타입이 application/json
    // @RequestBody로 데이터를 받아준다.
    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody MemberDTO memberDTO, PointDTO pointDTO, PointHistoryDTO pointHistoryDTO) {
        ResponseDTO<MemberDTO> responseDTO = new ResponseDTO<>();

        try {

            //회원가입 로직
            memberDTO.setPassword(passwordEncoder.encode(memberDTO.getPassword()));

            if(memberDTO.getUserNickname() == null) {
                memberDTO.setUserNickname(memberDTO.getUsername());
            }

            memberDTO.setRole("ROLE_USER");
            MemberDTO joinMemberDTO = memberService.join(memberDTO);
            joinMemberDTO.setPassword("");
            responseDTO.setItem(joinMemberDTO);
            responseDTO.setStatusCode(HttpStatus.OK.value());


            //포인트 로직
            pointDTO.setUsername(memberDTO.getUsername());
            pointDTO.setTotalPoint(3000);
            pointService.pointJoin(pointDTO);
            pointHistoryDTO.setUsername(memberDTO.getUsername());
            pointHistoryDTO.setPoint(3000);
            pointHistoryDTO.setPointCategory("NRU");
            pointHistoryService.pointHistoryJoin(pointHistoryDTO);

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
            if(e.getMessage().equalsIgnoreCase("not exist username")) {
                responseDTO.setErrorCode(200);
                responseDTO.setErrorMessage(e.getMessage());
            } else if(e.getMessage().equalsIgnoreCase("wrong password")) {
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
}
