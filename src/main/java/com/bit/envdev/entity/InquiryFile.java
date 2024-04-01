package com.bit.envdev.entity;

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
    private int inquiryFileId;

    @ManyToOne
    @JoinColumn(name = "inquiry_id", referencedColumnName = "inquiryId")
    private Inquiry inquiry;

    private String inquiryFileName;
    private String inquiryFilePath;
    private String inquiryFileOrigin;
}
