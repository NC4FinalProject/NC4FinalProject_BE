package com.bit.envdev.entity;

import com.bit.envdev.dto.InquiryDTO;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor

public class Inquiry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inquiryId")
    private long inquiryId;

    @Column(nullable = false)
    private String inquiryTitle;
    @Column(nullable = false)
    private String inquiryContent;

    @Column
    @Temporal(TemporalType.DATE)
    @CreationTimestamp
    private Date inquiryCrtDT;

    @Column
    @Temporal(TemporalType.DATE)
    @UpdateTimestamp
    private Date inquiryUdtDT;

    @Column(columnDefinition = "boolean default false")
    private boolean isPrivate;

    @Column(columnDefinition = "boolean default false")
    private boolean isSolved;

    @OneToMany(mappedBy = "inquiry", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Tag> tagList;

    @OneToMany(mappedBy = "inquiry", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<InquiryFile> inquiryFileList;

    @Column(nullable = false)
    @Builder.Default()
    private int inquiryView = 0;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    // 수동으로 setting
    @Column
    private int contentsId;

    @OneToMany(mappedBy = "inquiry", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<InquiryComment> inquiryCommentList;

    @Transient
    private String searchCondition;
    @Transient
    private String searchKeyword;

    public InquiryDTO toDTO() {
        return InquiryDTO.builder()
                .inquiryId(this.inquiryId)
                .contentsId(this.contentsId)
                .memberDTO(this.member.toDTO())
                .inquiryTitle(this.inquiryTitle)
                .inquiryContent(this.inquiryContent)
                .inquiryCrtDT(this.inquiryCrtDT)
                .inquiryUdtDT(this.inquiryUdtDT)
                .isPrivate(this.isPrivate)
                .isSolved(this.isSolved)
                .inquiryView(this.inquiryView)
                .inquiryFileDTOList(this.inquiryFileList != null ? this.inquiryFileList.stream().map(InquiryFile::toDTO).toList() : new ArrayList<>())
                .tagDTOList(this.tagList != null ? this.tagList.stream().map(Tag::toDTO).toList() : new ArrayList<>())
                .inquiryCommentDTOList(this.inquiryCommentList != null ? this.inquiryCommentList.stream().map(InquiryComment::toDTO).toList() : new ArrayList<>())
                .build();
    }
}
