package com.bit.envdev.dto;

import com.bit.envdev.entity.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class VideoReplyDTO {

    private String memberId;

    private int videoId;

    private int videoReplyId;

    private String videoReplyContent;
    public VideoReply toEntity(Video video, Member member) {
        return VideoReply.builder()
                .member(member)
                .video(video)
                .videoReplyId(this.videoReplyId)
                .videoReplyContent(this.videoReplyContent)
                .build();
    }

}
