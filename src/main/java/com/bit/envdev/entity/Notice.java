package com.bit.envdev.entity;

import com.bit.envdev.dto.NoticeDTO;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Long id;

    @Column(nullable = false)
    private String noticeTitle;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String noticeContent;

    @Column(nullable = false)
    private String noticeWriter ;

    @Column(updatable = false)
    private LocalDateTime noticeDate;

    @Column(columnDefinition = "integer default 0", nullable = false)
    @Builder.Default()
    private int view = 0;

    @OneToMany(mappedBy = "notice", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<NoticeFile> noticeFileList;

    public NoticeDTO toDTO() {
        return NoticeDTO.builder()
                .id(this.id)
                .noticeTitle(this.noticeTitle)
                .noticeContent(this.noticeContent)
                .noticeDate(this.noticeDate)
                .noticeWriter(this.noticeWriter)
                .noticeDate(this.noticeDate)
                .view(this.view)
                .noticeFileDTOList(noticeFileList.stream().map(NoticeFile::toDTO).toList())
                .build();
    }
}
