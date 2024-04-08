package com.bit.envdev.dto;

import com.bit.envdev.entity.Member;
import com.bit.envdev.entity.Point;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PointDTO {

    private Member member;
    private int value;
    private String reason;
    private String createdAt;

    public Point toEntity( ){
        return Point.builder()
                .member(this.member)
                .value(this.value)
                .reason(this.reason)
                .createdAt(LocalDate.parse(this.createdAt))
                .build();
        }
}


