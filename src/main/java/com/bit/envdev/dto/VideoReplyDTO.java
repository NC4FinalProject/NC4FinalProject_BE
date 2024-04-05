package com.bit.envdev.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class VideoReplyDTO {

    private int videoId;

    private int replyId;

    private String content;

}
