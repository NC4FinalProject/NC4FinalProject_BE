package com.bit.envdev.entity;

import com.bit.envdev.dto.FileDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long itemFileId;

    @Column(nullable = false)
    private String itemFileName;

    @Column(nullable = false)
    private String itemFilePath;

    @Column(nullable = false)
    private String itemFileOrigin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notice_id")
    @JsonBackReference
    private Notice notice;

    public FileDTO toDTO() {
        return FileDTO.builder()
                .itemFileId(this.itemFileId)
                .itemFileName(this.itemFileName)
                .itemFilePath(this.itemFilePath)
                .itemFileOrigin(this.itemFileOrigin)
                .build();
    }
}