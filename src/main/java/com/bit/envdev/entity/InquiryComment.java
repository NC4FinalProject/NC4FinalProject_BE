package com.bit.envdev.entity;

import com.bit.envdev.dto.InquiryCommentDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;


@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class InquiryComment  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long inquiryCommentId;

    @Column
    @Temporal(TemporalType.DATE)
    @CreationTimestamp
    private Date inquiryCommentCrtDT;

    @Column
    @Temporal(TemporalType.DATE)
    @UpdateTimestamp
    private Date inquiryCommentUdtDT;

    @Column(nullable = false)
    private String inquiryCommentContent;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="inquiry_id", referencedColumnName = "inquiryId")
    private Inquiry inquiry;

    public InquiryCommentDTO toDTO() {
        return InquiryCommentDTO.builder()
                .inquiryCommentId(this.inquiryCommentId)
                .inquiryCommentCrtDT(this.inquiryCommentCrtDT)
                .inquiryCommentUdtDT(this.inquiryCommentUdtDT)
                .inquiryCommentContent(this.inquiryCommentContent)
                .memberDTO(this.member.toDTO())
                .inquiryId(this.inquiry.getInquiryId())
                .build();
    }
}
