package com.bit.envdev.dto;

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
    private List<FileDTO> noticeFileDTOList;
    private String searchCondition;
    private String searchKeyword;
    private String profileImageUrl;
    private boolean likeNoticeByUser;
    public Notice toEntity() {
        return Notice.builder()
                .id(this.id)
                .noticeTitle(this.noticeTitle)
                .noticeContent(this.noticeContent)
                .noticeWriter(this.noticeWriter)
                .noticeDate(this.noticeDate)
                .noticeFileList(new ArrayList<>())
                .view(this.view)
                .build();
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}