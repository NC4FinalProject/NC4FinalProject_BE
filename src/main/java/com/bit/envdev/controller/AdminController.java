package com.bit.envdev.controller;

import com.bit.envdev.constant.Role;
import com.bit.envdev.dto.*;
import com.bit.envdev.service.ContentsService;
import com.bit.envdev.service.MemberService;
import com.bit.envdev.service.NoticeService;
import com.bit.envdev.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @GetMapping("/main")
    private ResponseEntity<?> userChart() {

        try {
            List<NoticeDTO> notices = noticeService.findAll();
            System.out.println("1111111111111111111111111111");
            List<MemberDTO> recentUsers = memberService.find4User();
            System.out.println("222222222222222222222222222");
            List<MemberGraphDTO> registrationCounts = memberService.getRegistrationCount();
            System.out.println("33333333333333333333333333");
            List<MemberGraphDTO> monthlyCounts = memberService.getMonthlyUserCount();
            System.out.println("444444444444444444444444");
            List<MemberGraphDTO> monthlytotalUserCount = memberService.getMonthTotalUserCount();
            System.out.println("5555555555555555555555555555");
            long preTeacherCount = memberService.getPreTeacherCount();
            System.out.println("666666666666666666666666");
            List<MemberDTO> preTeachers = memberService.findByRole();
            System.out.println("7777777777777777777777777");
            List<MemberGraphDTO>  daliyOutUserCount = memberService.getDailyOutUserCount();
            System.out.println("8888888888888888888888888888888");
            List<MemberGraphDTO>  monthlyOutUserCount = memberService.getMonthlyOutUserCount();
            long todayUserCount = memberService.getTodayUserCount();
            List<ContentsDTO> contents = contentsService.get4Contents();

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
                                          @RequestParam(name = "searchCondition", required = false) String  searchCondition,
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

        try {
            MemberDTO member = memberService.findById(id);
            return ResponseEntity.ok(member);
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
}
