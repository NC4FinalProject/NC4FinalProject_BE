package com.bit.envdev.controller;

import com.bit.envdev.dto.ResponseDTO;
import com.bit.envdev.entity.CustomUserDetails;
import com.bit.envdev.entity.PointHistory;
import com.bit.envdev.service.PointHistoryService;
import com.bit.envdev.service.PointService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class PointController {

    private final PointService pointService;
    private final PointHistoryService pointHistoryService;

    @Operation(method = "GET", description = "프론트에 포인트내역 반환")
    @GetMapping("/pointHistory")
    public ResponseEntity<?> getPointHistory(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
//        ResponseDTO<PointHistoryDTO> responseDTO = new ResponseDTO<>();
        ResponseDTO<PointHistory> responseDTO = new ResponseDTO<>();

        try {
//            List<PointHistoryDTO> PointHistoryDTOList = pointHistoryService.findByUsername(customUserDetails.getUsername());

            List<PointHistory> pointHistoryList = pointHistoryService.getPointHistory(customUserDetails.getUsername());


//            responseDTO.setItems(PointHistoryDTOList);
            responseDTO.setItems(pointHistoryList);
            responseDTO.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok(responseDTO);
        } catch(Exception e) {
            responseDTO.setErrorCode(301);
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }
    @PostMapping("/charge")
    public void chargePoint(@RequestParam int point,
                            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        String username = customUserDetails.getUsername();
        pointService.pointCharge(point, username);
        String pointCategory = "cru";
        pointHistoryService.setPointHistory(point, username, pointCategory);
    }

}
