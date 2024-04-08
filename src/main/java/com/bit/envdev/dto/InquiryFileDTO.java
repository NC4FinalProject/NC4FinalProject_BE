package com.bit.envdev.dto;

import com.bit.envdev.entity.Inquiry;
import com.bit.envdev.entity.InquiryFile;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InquiryFileDTO {

    private long inquiryFileId;
    private String inquiryFileName;
    private String inquiryFilePath;
    private String inquiryFileOrigin;

    public InquiryFile toEntity() {
        return InquiryFile.builder()
                .inquiryFileId(this.inquiryFileId)
                .inquiryFileName(this.inquiryFileName)
                .inquiryFilePath(this.inquiryFilePath)
                .inquiryFileOrigin(this.inquiryFileOrigin)
                .build();
    }

}
