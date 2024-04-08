package com.bit.envdev.service;

import com.bit.envdev.dto.MemberDTO;
import com.bit.envdev.dto.ReportDTO;

public interface ReportService {
    ReportDTO regReport(ReportDTO reportDTO, String refType, Long refId, MemberDTO reporter);

    void updateState(Long id, int code);
}