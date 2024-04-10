package com.bit.envdev.dto;

import com.bit.envdev.entity.InquiryComment;
import com.bit.envdev.entity.InquiryCommentLike;
import com.bit.envdev.entity.Member;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InquiryCommentLikeDTO {
    private long inquiryCommentId;
    private long member;

    public InquiryCommentLike toEntity() {
       return InquiryCommentLike.builder()
               .inquiryComment(InquiryComment.builder().inquiryCommentId(this.inquiryCommentId).build())
               .member(Member.builder().memberId(this.member).build())
               .build();
    }
}
