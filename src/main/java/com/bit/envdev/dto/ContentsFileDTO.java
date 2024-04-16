package com.bit.envdev.dto;

import com.bit.envdev.entity.Contents;
import com.bit.envdev.entity.ContentsFile;
import com.bit.envdev.entity.InquiryFile;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ContentsFileDTO {
    private long contentsId;
    private long contentsFileId;
    private String contentsFileName;
    private String contentsFilePath;
    private String contentsFileOrigin;

    public ContentsFile toEntity(Contents contents) {
        return ContentsFile.builder()
                .contentsFileId(this.contentsFileId)
                .contentsFileName(this.contentsFileName)
                .contentsFilePath(this.contentsFilePath)
                .contentsFileOrigin(this.contentsFileOrigin)
                .contents(contents)
                .build();
    }
}
