package com.bit.envdev.dto;

import com.bit.envdev.entity.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class InquiryDTO {
    private long inquiryId;
    private int contentsId;
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
    private long commentCount;
    private List<InquiryCommentDTO> inquiryCommentDTOList;
    private long likeCount;
    private String author;


    public Inquiry toEntity() {
        return Inquiry.builder()
                .inquiryId(this.inquiryId)
                .contentsId(this.contentsId)
                .inquiryTitle(this.inquiryTitle)
                .inquiryContent(this.inquiryContent)
                .inquiryCrtDT(this.inquiryCrtDT)
                .inquiryUdtDT(this.inquiryUdtDT)
                .isPrivate(this.isPrivate)
                .isSolved(this.isSolved)
                .inquiryView(this.inquiryView)
                .member(this.memberDTO.toEntity())
                .inquiryFileList(new ArrayList<>())
                .tagList(new ArrayList<>())
                .build();
    }
}
