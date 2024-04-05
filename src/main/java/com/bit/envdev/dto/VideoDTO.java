package com.bit.envdev.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class VideoDTO {

    private int contentsId;

    private int videoId;

    private String videoTitle;

    private String videoPath;

    private List<VideoReplyDTO> ReplyDTOList;

}
