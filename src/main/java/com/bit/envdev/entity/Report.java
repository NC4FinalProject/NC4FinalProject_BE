package com.bit.envdev.entity;

import com.bit.envdev.constant.ReportRefType;
import com.bit.envdev.constant.ReportState;
import com.bit.envdev.converter.ReportRefTypeConverter;
import com.bit.envdev.converter.ReportStateConverter;
import com.bit.envdev.dto.ReportDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    @ManyToOne
    @JoinColumn(name = "id")
    private Member reporter;

    @Column
    private String text;

    @Column(nullable = false)
    @Convert(converter = ReportRefTypeConverter.class)
    private ReportRefType refType;

    @Column
    private Long refId;

    @Column
    @Convert(converter = ReportStateConverter.class)
    @Builder.Default
    private ReportState state = ReportState.HOLD;

    @Column(updatable = false, nullable = false)
    @Builder.Default
    private LocalDateTime reportDate = LocalDateTime.now();

    public ReportDTO toDTO() {
        return ReportDTO.builder()
                .id(this.reportId)
                .reporter(this.reporter.toDTO())
                .text(this.text)
                .refType(this.refType.getDesc())
                .refId(this.refId)
                .state(this.state.getLegacyCode())
                .reportDate(String.valueOf(this.reportDate))
                .build();
    }
}
