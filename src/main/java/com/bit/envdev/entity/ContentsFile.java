package com.bit.envdev.entity;

import com.bit.envdev.dto.ContentsFileDTO;
import com.bit.envdev.dto.InquiryFileDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContentsFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long contentsFileId;

    @ManyToOne
    @JoinColumn(name = "contentsId")
    @JsonBackReference
    private Contents contents;

    private String contentsFileName;
    private String contentsFilePath;
    private String contentsFileOrigin;

    public ContentsFileDTO toDTO() {
        return ContentsFileDTO.builder()
                .contentsFileId(this.contentsFileId)
                .contentsFileName(this.contentsFileName)
                .contentsFilePath(this.contentsFilePath)
                .contentsFileOrigin(this.contentsFileOrigin)
                .contentsId(this.contents.getContentsId())
                .build();
    }
}
