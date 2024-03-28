package com.bit.envdev.dto;

import com.bit.envdev.entity.Notice;
import com.bit.envdev.entity.NoticeFile;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class FileDTO {
    private long itemFileId;
    private long noticeId;
    private String itemFileName;
    private String itemFilePath;
    private String itemFileOrigin;

    public NoticeFile toEntity(Notice notice) {
        return NoticeFile.builder()
                .itemFileId(this.itemFileId)
                .itemFileName(this.itemFileName)
                .itemFilePath(this.itemFilePath)
                .itemFileOrigin(this.itemFileOrigin)
                .notice(notice)
                .build();
    }
}
