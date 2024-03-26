package com.bit.envdev.dto;

import com.bit.envdev.entity.Member;
import com.bit.envdev.entity.Notice;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeDTO {
    private Long id;
    private String noticeTitle;
    private String noticeContent;
    private String noticeWriter;
    private LocalDateTime noticeDate;
    private int view;
    private long likeCnt;
    private List<FileDTO> noticeFileDTOList;
    private String searchCondition;
    private String searchKeyword;
    private String profileImageUrl;

    public Notice toEntity() {
        return Notice.builder()
                .id(this.id)
                .noticeTitle(this.noticeTitle)
                .noticeContent(this.noticeContent)
                .noticeWriter(this.noticeWriter)
                .noticeDate(this.noticeDate)
                .view(this.view)
                .likeCnt(this.likeCnt)
                .build();
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}