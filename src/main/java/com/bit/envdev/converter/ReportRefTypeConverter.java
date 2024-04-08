package com.bit.envdev.converter;

import com.bit.envdev.constant.ReportRefType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Convert;

@Convert
public class ReportRefTypeConverter implements AttributeConverter<ReportRefType, String> {
    @Override
    public String convertToDatabaseColumn(ReportRefType reportRefType) {
        return reportRefType .getLegacyCode();
    }

    @Override
    public ReportRefType convertToEntityAttribute(String s) {
        return ReportRefType.ofCode(s);
    }
}
