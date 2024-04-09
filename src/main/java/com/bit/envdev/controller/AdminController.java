package com.bit.envdev.controller;

import com.bit.envdev.constant.Role;
import com.bit.envdev.dto.MemberDTO;
import com.bit.envdev.dto.MemberGraphDTO;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final MemberService memberService;
    private final NoticeService noticeService;

    @GetMapping("/main")
    private ResponseEntity<?> userChart() {
        try {
            List<NoticeDTO> notices = noticeService.findAll();
            List<MemberDTO> recentUsers = memberService.find4User();
            List<MemberGraphDTO> registrationCounts = memberService.getRegistrationCount();
            List<MemberGraphDTO> monthlyCounts = memberService.getMonthlyUserCount();
            List<MemberGraphDTO> monthlytotalUserCount = memberService.getMonthTotalUserCount();
            long preTeacherCount = memberService.getPreTeacherCount();
            List<MemberDTO> preTeachers = memberService.findByRole();
            List<MemberGraphDTO>  daliyOutUserCount = memberService.getDailyOutUserCount();
            List<MemberGraphDTO>  monthlyOutUserCount = memberService.getMonthlyOutUserCount();
            long todayUserCount = memberService.getTodayUserCount();

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
        System.out.println("id = " + id);
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
}
