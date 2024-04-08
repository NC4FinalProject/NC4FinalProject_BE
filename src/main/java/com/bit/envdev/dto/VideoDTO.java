package com.bit.envdev.dto;

import com.bit.envdev.entity.Contents;
import com.bit.envdev.entity.Section;
import com.bit.envdev.entity.Video;
import lombok.*;

import java.util.ArrayList;
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

    private String videoTime;

    private String videoStorageId;

    private String videoPath;

    private List<VideoReplyDTO> videoReplyList;

    public Video toEntity(Contents contents) {
        return Video.builder()
                .contents(contents)
                .videoId(this.videoId)
                .videoTitle(this.videoTitle)
                .videoTime(this.videoTime)
                .videoStorageId(this.videoStorageId)
                .videoPath(this.videoPath)
                .videoReplyList(new ArrayList<>())
                .build();
    }

}