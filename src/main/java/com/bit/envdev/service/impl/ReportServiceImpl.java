package com.bit.envdev.service.impl;

import com.bit.envdev.constant.ReportRefType;
import com.bit.envdev.dto.MemberDTO;
import com.bit.envdev.dto.ReportDTO;
import com.bit.envdev.entity.Report;
import com.bit.envdev.repository.ReportRepository;
import com.bit.envdev.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final ReportRepository reportRepository;

    @Override
    public ReportDTO regReport(ReportDTO reportDTO, String refType, Long refId, MemberDTO reporter) throws IllegalArgumentException {
        ReportRefType.ofCode(refType);
        reportDTO.setRefType(refType);
        reportDTO.setRefId(refId);
        reportDTO.setReporter(reporter);
        Report report = reportRepository.save(reportDTO.toEntity());
        return report.toDTO();
    }

    @Override
    public void updateState(Long id, int code) {
        reportRepository.updateSate(id, code);
    }
}
