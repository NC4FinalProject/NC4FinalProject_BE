package com.bit.envdev.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberGraphDTO {
    private LocalDateTime registration_date;
    private Long user_count;
}
