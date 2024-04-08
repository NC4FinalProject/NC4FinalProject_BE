package com.bit.envdev.constant;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum BlockState {
    BLOCKING("정지", 0),
    UNBLOCKED("해지", 1);

    private final String desc;
    private final Integer legacyCode;

    BlockState(String desc, int legacyCode) {
        this.desc = desc;
        this.legacyCode = legacyCode;
    }

    public static BlockState ofCode(int legacyCode) {
        return Arrays.stream(BlockState.values())
                .filter(v -> v.getLegacyCode() == legacyCode)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상태 코드입니다."));
    }
}