package com.bit.envdev.entity;

import com.bit.envdev.dto.InquiryFileDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class InquiryFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long inquiryFileId;

    @ManyToOne
    @JoinColumn(name = "inquiryId")
    @JsonBackReference
    private Inquiry inquiry;

    private String inquiryFileName;
    private String inquiryFilePath;
    private String inquiryFileOrigin;

    public InquiryFileDTO toDTO() {
        return InquiryFileDTO.builder()
                .inquiryFileId(this.inquiryFileId)
                .inquiryFileName(this.inquiryFileName)
                .inquiryFilePath(this.inquiryFilePath)
                .inquiryFileOrigin(this.inquiryFileOrigin)
                .inquiryId(this.inquiry.getInquiryId())
                .build();
    }
}
