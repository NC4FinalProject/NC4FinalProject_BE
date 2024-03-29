package com.bit.envdev.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class NoticeLikeDTO {
    private Long noticeId;
    private long member;
}
