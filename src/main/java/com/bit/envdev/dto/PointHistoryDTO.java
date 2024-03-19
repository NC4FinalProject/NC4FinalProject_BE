package com.bit.envdev.dto;

import com.bit.envdev.entity.PointHistory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PointHistoryDTO {
    private Long userPointId;

    private String username;

    private int Point;

    private String orderId;

    private String pointCategory;

    private LocalDate pointHistoryModifiedDate;

    public PointHistory toEntity(){
        return PointHistory.builder()
                .username(username)
                .orderId(UUID.randomUUID().toString())
                .Point(Point)
                .pointCategory(pointCategory)
                .pointHistoryModifiedDate(pointHistoryModifiedDate)
                .build();
    }
}

