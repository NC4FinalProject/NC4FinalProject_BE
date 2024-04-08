package com.bit.envdev.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ThumbnailDTO {

    private int contentsId;

    private int thumbnailId;

    private String thumbnailPath;

}
