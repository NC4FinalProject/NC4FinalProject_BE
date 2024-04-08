package com.bit.envdev.dto;

import com.bit.envdev.constant.ReportRefType;
import com.bit.envdev.constant.ReportState;
import com.bit.envdev.dto.InquiryDTO;
import com.bit.envdev.dto.InquiryDTO;
import com.bit.envdev.dto.MemberDTO;
import com.bit.envdev.entity.Report;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportDTO {
    private Long id;
    private MemberDTO reporter;
    private String text;
    private String refType;
    private Long refId;
    private InquiryDTO refInquiry;
    private MemberDTO refMember;
    private int state;
    private String reportDate;

    public Report toEntity() {
        return Report.builder()
                .reportId(this.id)
                .reporter(this.reporter.toEntity())
                .text(this.text)
                .refType(ReportRefType.ofCode(this.refType))
                .refId(this.refId)
                .state(ReportState.ofCode(this.state))
                .reportDate((LocalDateTime.parse(this.reportDate)))
                .build();
    }
}
