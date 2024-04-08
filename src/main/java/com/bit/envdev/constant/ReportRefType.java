package com.bit.envdev.constant;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum ReportRefType {
    INQUIRY("질문", "INQ"),
    INQUIRY_COMMENT("댓글", "INQ_COM"),
    MEMBER("회원", "MEM");

    private final String desc;
    private final String legacyCode;

    ReportRefType(String desc, String legacyCode) {
        this.desc = desc;
        this.legacyCode = legacyCode;
    }

    public static ReportRefType ofCode(String legacyCode) {
        return Arrays.stream(ReportRefType.values())
                .filter(v -> v.getLegacyCode().equals(legacyCode))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 참조 타입입니다."));
    }
}
