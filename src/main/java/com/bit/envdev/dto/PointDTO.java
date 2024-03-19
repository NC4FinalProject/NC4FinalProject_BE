package com.bit.envdev.dto;

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
    private String username;
    private int totalPoint;
    private LocalDate pointModifiedDate;

    public Point toEntity( ){
    return Point.builder()
            .username(username)
            .totalPoint(totalPoint)
            .pointModifiedDate(LocalDate.now())
            .build();
}

}


