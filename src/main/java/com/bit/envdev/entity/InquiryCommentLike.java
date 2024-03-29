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

public class InquiryCommentLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long inquiryCommentLikeId;

    @ManyToOne
    @JoinColumn(name = "inquiry_comment_id", referencedColumnName = "inquiry_comment_id")
    private InquiryComment inquiryComment;

}