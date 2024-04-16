package com.bit.envdev.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(InquiryCommentLikeId.class)

public class InquiryCommentLike {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inquiry_comment_id")
    private InquiryComment inquiryComment;


    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")

    private Member member;
}