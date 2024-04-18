package com.bit.envdev.dto;

import com.bit.envdev.entity.Inquiry;
import com.bit.envdev.entity.InquiryComment;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InquiryCommentDTO {

    private long inquiryId;
    private long inquiryCommentId;
    private Date inquiryCommentCrtDT;
    private Date inquiryCommentUdtDT;
    private String inquiryCommentContent;
    private MemberDTO memberDTO;
    private boolean isCommentLike;
    private long inquiryCommentLikeCount;

    public InquiryComment toEntity(Inquiry inquiry) {
        return InquiryComment.builder()
                .inquiryCommentId(this.inquiryCommentId)
                .inquiryCommentCrtDT(this.inquiryCommentCrtDT)
                .inquiryCommentUdtDT(this.inquiryCommentUdtDT)
                .inquiryCommentContent(this.inquiryCommentContent)
                .member(this.memberDTO.toEntity())
                .inquiry(inquiry)
                .build();
    }

}
