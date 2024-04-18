package com.bit.envdev.controller;

import com.bit.envdev.constant.Role;
import com.bit.envdev.dto.*;
import com.bit.envdev.entity.CustomUserDetails;
import com.bit.envdev.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final MemberService memberService;
    private final NoticeService noticeService;
    private final PointService pointService;
    private final ContentsService contentsService;
    private final QnaService qnaService;
    private final InquiryService inquiryService;
    private final ReviewService reviewService;
    @GetMapping("/main")
    private ResponseEntity<?> userChart(@AuthenticationPrincipal CustomUserDetails customUserDetails) {

        try {
            List<NoticeDTO> notices = noticeService.findAll();
            List<MemberDTO> recentUsers = memberService.find4User();
            List<MemberGraphDTO> registrationCounts = memberService.getRegistrationCount();
            List<MemberGraphDTO> monthlyCounts = memberService.getMonthlyUserCount();
            List<MemberGraphDTO> monthlytotalUserCount = memberService.getMonthTotalUserCount();
            long preTeacherCount = memberService.getPreTeacherCount();
            List<MemberDTO> preTeachers = memberService.findByRole();
            List<MemberGraphDTO> daliyOutUserCount = memberService.getDailyOutUserCount();
            List<MemberGraphDTO> monthlyOutUserCount = memberService.getMonthlyOutUserCount();
            long todayUserCount = memberService.getTodayUserCount();
            List<ContentsDTO> contents = contentsService.get4Contents();
            Role role = customUserDetails.getMember().getRole();
            List<QnaDTO> qnauser = qnaService.get4Qna();
            long qnaUserCount = qnaService.getQnaUserCount();
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("notices", notices);
            responseData.put("recentUsers", recentUsers);
            responseData.put("registrationCounts", registrationCounts);
            responseData.put("monthlytotalUserCount", monthlytotalUserCount);
            responseData.put("monthlyCounts", monthlyCounts);
            responseData.put("preTeacherCount", preTeacherCount);
            responseData.put("daliyOutUserCount", daliyOutUserCount);
            responseData.put("monthlyOutUserCount", monthlyOutUserCount);
            responseData.put("preTeachers", preTeachers);
            responseData.put("todayUserCount", todayUserCount);
            responseData.put("contents", contents);
            responseData.put("userRole", role);
            responseData.put("qnauser", qnauser);
            responseData.put("qnaUserCount", qnaUserCount);
            return ResponseEntity.ok(responseData);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("errorCode", 101);
            errorResponse.put("errorMessage", e.getMessage());
            errorResponse.put("statusCode", HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @GetMapping("/user")
    private ResponseEntity<?> getUserList(@PageableDefault(page = 0, size = 15) Pageable pageable,
                                          @RequestParam(name = "searchCondition", required = false) String searchCondition,
                                          @RequestParam(name = "searchKeyword", required = false) String searchKeyword) {
        ResponseDTO<MemberDTO> responseDTO = new ResponseDTO<>();
        try {
            if (searchCondition.equals("all")) {
                Page<MemberDTO> allUsers = memberService.searchData(pageable, searchKeyword);
                responseDTO.setPageItems(allUsers);
                responseDTO.setStatusCode(HttpStatus.OK.value());
                return ResponseEntity.ok(allUsers);
            }
            Role role = Role.valueOf(searchCondition);
            Page<MemberDTO> usersPage = memberService.searchAll(pageable, searchKeyword, role);
            responseDTO.setPageItems(usersPage);
            responseDTO.setStatusCode(HttpStatus.OK.value());
            return ResponseEntity.ok(usersPage);

        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("errorCode", 101);
            errorResponse.put("errorMessage", e.getMessage());
            errorResponse.put("statusCode", HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @PostMapping("/user/memo")
    private ResponseEntity<?> updateMemo(@RequestBody MemberDTO memberDTO) {
        try {
            memberService.updateUserMemo(memberDTO);

            return ResponseEntity.ok(null);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("errorCode", 101);
            errorResponse.put("errorMessage", e.getMessage());
            errorResponse.put("statusCode", HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @GetMapping("/user/{id}")
    private ResponseEntity<?> getUser(@PathVariable("id") long id) {
        Map<String, Object> responseData = new HashMap<>();
        try {
            MemberDTO member = memberService.findById(id);
            long contentCount = contentsService.countByMemberId(member.toEntity());
            long inqueryCount = inquiryService.countByMemberId(member.toEntity());
            long reviewCount = reviewService.countByMemberId(member.toEntity());
            long qnaCount = qnaService.countByMemberId(member.toEntity());
            responseData.put("member", member);
            responseData.put("contentCount", contentCount);
            responseData.put("inqueryCount", inqueryCount);
            responseData.put("reviewCount", reviewCount);
            responseData.put("qnaCount", qnaCount);
            return ResponseEntity.ok(responseData);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("errorCode", 101);
            errorResponse.put("errorMessage", e.getMessage());
            errorResponse.put("statusCode", HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @PostMapping("/user/{id}")
    private ResponseEntity<?> changePw(@PathVariable("id") long id, @RequestBody MemberDTO memberDTO) {
        try {
            MemberDTO member = memberService.findById(id);
            memberService.changePw(member, memberDTO.getPassword());
            return ResponseEntity.ok(null);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("errorCode", 101);
            errorResponse.put("errorMessage", e.getMessage());
            errorResponse.put("statusCode", HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @PutMapping("/user/{id}")
    private ResponseEntity<?> changeRole(@PathVariable("id") long id, @RequestBody MemberDTO memberDTO) {
        try {
            MemberDTO member = memberService.findById(id);
            memberService.changeRole(member, memberDTO.getRole());
            return ResponseEntity.ok(null);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("errorCode", 101);
            errorResponse.put("errorMessage", e.getMessage());
            errorResponse.put("statusCode", HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @PostMapping("/user/point/{id}")
    private ResponseEntity<?> insertPoint(@PathVariable("id") long id, @RequestBody PointDTO pointDTO) {
        try {
            MemberDTO memberDTO = memberService.findById(id);
            pointDTO.setMember(memberDTO.toEntity());
            pointService.pointJoinWithBuilder(pointDTO.getMember(), pointDTO.getValue(), pointDTO.getReason());
            System.out.println(pointDTO);
            return ResponseEntity.ok(null);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("errorCode", 101);
            errorResponse.put("errorMessage", e.getMessage());
            errorResponse.put("statusCode", HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @GetMapping("/contents")
    private ResponseEntity<?> getContentsList(@PageableDefault(page = 0, size = 15) Pageable pageable,
                                              @RequestParam(name = "searchCondition", required = false) String searchCondition,
                                              @RequestParam(name = "searchKeyword", required = false) String searchKeyword) {
        ResponseDTO<ContentsDTO> responseDTO = new ResponseDTO<>();
        try {
                Page<ContentsDTO> Contents = contentsService.searchData(pageable, searchKeyword, searchCondition);
                responseDTO.setPageItems(Contents);
                responseDTO.setStatusCode(HttpStatus.OK.value());
                return ResponseEntity.ok(Contents);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("errorCode", 101);
            errorResponse.put("errorMessage", e.getMessage());
            errorResponse.put("statusCode", HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @GetMapping("/qna")
    private ResponseEntity<?> getQna(@PageableDefault(page = 0, size = 15) Pageable pageable,
                                              @RequestParam(name = "searchCondition", required = false) String searchCondition,
                                              @RequestParam(name = "searchKeyword", required = false) String searchKeyword) {
        ResponseDTO<QnaDTO> responseDTO = new ResponseDTO<>();
        try {
            Page<QnaDTO> qanUser = qnaService.searchData(pageable, searchKeyword, searchCondition);
            responseDTO.setPageItems(qanUser);
            responseDTO.setStatusCode(HttpStatus.OK.value());
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("errorCode", 101);
            errorResponse.put("errorMessage", e.getMessage());
            errorResponse.put("statusCode", HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(errorResponse);

        }
    }
    @PostMapping("/answerqna")
    private ResponseEntity<?> answerQna(@RequestBody QnaDTO qnaDTO) {
        ResponseDTO<QnaDTO> responseDTO = new ResponseDTO<>();
        try {
            qnaService.answered(qnaDTO);
            return ResponseEntity.ok(null);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("errorCode", 555);
            errorResponse.put("errorMessage", e.getMessage());
            errorResponse.put("statusCode", HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
}
