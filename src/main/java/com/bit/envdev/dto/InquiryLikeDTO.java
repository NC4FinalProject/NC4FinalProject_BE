package com.bit.envdev.dto;

import com.bit.envdev.entity.Inquiry;
import com.bit.envdev.entity.InquiryLike;
import com.bit.envdev.entity.Member;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InquiryLikeDTO {
    private long inquiryId;
    private long member;

    public InquiryLike toEntity () {
        return InquiryLike.builder()
                .inquiry(Inquiry.builder().inquiryId(this.inquiryId).build())
                .member(Member.builder().memberId(this.member).build())
                .build();
    }
}
