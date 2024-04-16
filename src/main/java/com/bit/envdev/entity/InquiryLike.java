package com.bit.envdev.entity;

import com.bit.envdev.dto.InquiryLikeDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(InquiryLikeId.class)

public class InquiryLike {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="inquiry_id")
    private Inquiry inquiry;


    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    public InquiryLikeDTO toDTO() {
        return InquiryLikeDTO.builder()
                .inquiryId(this.inquiry.getInquiryId())
                .member(this.member.getMemberId())
                .build();
    }
}
