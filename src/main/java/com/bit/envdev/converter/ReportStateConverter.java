package com.bit.envdev.converter;

import com.bit.envdev.constant.ReportState;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Convert;

@Convert
public class ReportStateConverter implements AttributeConverter<ReportState, Integer> {
    @Override
    public Integer convertToDatabaseColumn(ReportState reportState) {
        return reportState.getLegacyCode();
    }

    @Override
    public ReportState convertToEntityAttribute(Integer i) {
        return ReportState.ofCode(i);
    }
}
