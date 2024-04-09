package com.bit.envdev.controller;

import com.bit.envdev.constant.ReportState;
import com.bit.envdev.dto.ReportDTO;
import com.bit.envdev.dto.ResponseDTO;
import com.bit.envdev.entity.CustomUserDetails;
import com.bit.envdev.entity.Member;
import com.bit.envdev.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @PostMapping("/reg")
    public ResponseEntity<?> regReport(@RequestParam(value = "reportDTO") ReportDTO reportDTO,
                                       @RequestParam(value = "refType") String refType,
                                       @RequestParam(value = "refId") Long refId,
                                       @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        ResponseDTO<ReportDTO> responseDTO = new ResponseDTO<>();
        try {
            Member reporter = customUserDetails.getMember();
            ReportDTO regReportDTO = reportService.regReport(reportDTO, refType, refId, reporter.toDTO());
            responseDTO.setItem(regReportDTO);
            responseDTO.setStatusCode(HttpStatus.OK.value());
            return ResponseEntity.ok(responseDTO);
        } catch (IllegalArgumentException e) {
            responseDTO.setErrorCode(100);
            responseDTO.setErrorMessage("존재하지 않는 참조 타입입니다.");
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(responseDTO);
        } catch (Exception e) {
            responseDTO.setErrorCode(101);
            responseDTO.setErrorMessage("예상치 못한 오류가 발생했습니다.");
            responseDTO.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
        }
    }

    @PutMapping("/state")
    public ResponseEntity<?> updateState(@RequestParam(value = "reportId") Long id,
                                         @RequestParam(value = "state") String legacyCode) {
        ResponseDTO<ReportDTO> responseDTO = new ResponseDTO<>();
        try {
            int state = ReportState.legacyCodeOfDesc(legacyCode);
            reportService.updateState(id, state);
            responseDTO.setStatusCode(HttpStatus.OK.value());
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            responseDTO.setErrorCode(100);
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }
}
