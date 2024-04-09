package com.bit.envdev.constant;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum ReportState {
    HOLD("보류", 0),
    RETURN("반려", 1),
    PROCESS("처리", 2);

    private final String desc;
    private final int legacyCode;

    ReportState(String desc, int legacyCode) {
        this.desc = desc;
        this.legacyCode = legacyCode;
    }

    public static ReportState ofCode(int legacyCode) {
        return Arrays.stream(ReportState.values())
                .filter(v -> v.getLegacyCode() == legacyCode)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상태 코드입니다."));
    }

    public static int legacyCodeOfDesc(String desc) {
        for (ReportState state : ReportState.values()) {
            if (state.getDesc().equals(desc)) {
                return state.getLegacyCode();
            }
        }
        throw new IllegalArgumentException("존재하지 않는 상태 설명입니다.");
    }
}