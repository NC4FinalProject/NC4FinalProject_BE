package com.bit.envdev.controller;

import com.bit.envdev.dto.ResponseDTO;
import com.bit.envdev.dto.ReportDTO;
import com.bit.envdev.service.BlockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class BlockController {
    private final BlockService blockService;

    @PostMapping("/block")
    public ResponseEntity<?> block(@RequestParam(value = "refType") String refType,
                                   @RequestParam(value = "refId") Long refId,
                                   @RequestParam(value = "period") int period) {
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        try {
            blockService.block(refType, refId, period);
            // responseDTO.setItem(regReportDTO);
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

    @PutMapping("/unblock")
    public ResponseEntity<?> holdReport(@RequestParam(value = "refType") String refType,
                                        @RequestParam(value = "refId") Long refId) {
        ResponseDTO<ReportDTO> responseDTO = new ResponseDTO<>();
        try {
            blockService.unblock(refType, refId);
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
