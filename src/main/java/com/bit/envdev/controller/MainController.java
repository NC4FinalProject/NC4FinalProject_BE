package com.bit.envdev.controller;

import com.bit.envdev.entity.CustomUserDetails;
import com.bit.envdev.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MainController {
    @RequestMapping("/")
    private ResponseEntity<?> getUserInfo(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Map<String, Object> result= new HashMap<>();
        try {
                if (customUserDetails == null) {
                    result.put("id", 0);
                    result.put("name", "비회원");
                    result.put("profile", "default.png");
                    return ResponseEntity.ok(result);
                } else {
                    Member member = customUserDetails.getMember();
                    result.put("id", member.getUsername());
                    result.put("name", member.getUserNickname());
                    result.put("profile", member.getProfileFile());
                    return ResponseEntity.ok(result);
                }
        } catch (Exception e) {
            result.put("id", "에러");
            result.put("name", "에러");
            result.put("profile", "default.png");
            return ResponseEntity.ok(result);
        }
    }
}