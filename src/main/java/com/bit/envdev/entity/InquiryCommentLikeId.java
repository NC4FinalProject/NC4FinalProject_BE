package com.bit.envdev.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter

public class InquiryCommentLikeId implements Serializable {
    private long inquiryComment;
    private long member;
}
