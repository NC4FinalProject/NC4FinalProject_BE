package com.bit.envdev.dto;

import com.bit.envdev.entity.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InquiryDTO {
    private long inquiryId;
    private long contentsId;
    private String contentsTitle;
    private String inquiryTitle;
    private String inquiryContent;
    private Date inquiryCrtDT;
    private Date inquiryUdtDT;
    private boolean isPrivate;
    private boolean isSolved;
    private List<TagDTO> tagDTOList;
    private MemberDTO memberDTO;
    private List<InquiryFileDTO> inquiryFileDTOList;
    private int inquiryView;
    private String searchCondition;
    private String searchKeyword;
    private boolean isLike;

    public Inquiry toEntity() {
        return Inquiry.builder()
                .inquiryId(this.inquiryId)
                .inquiryTitle(this.inquiryTitle)
                .inquiryContent(this.inquiryContent)
                .inquiryCrtDT(this.inquiryCrtDT)
                .inquiryUdtDT(this.inquiryUdtDT)
                .isPrivate(this.isPrivate)
                .isSolved(this.isSolved)
                .inquiryView(this.inquiryView)
                .inquiryFileList(this.inquiryFileDTOList.stream().map(InquiryFileDTO::toEntity).toList())
                .tagList(this.tagDTOList.stream().map(TagDTO::toEntity).toList())
                .build();
    }
}
