package com.bit.envdev.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ContentsBookmarkDTO {
    private int contentsId;
    private long memberId;
}
