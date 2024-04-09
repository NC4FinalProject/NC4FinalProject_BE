package com.bit.envdev.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter

public class InquiryCommentLikeId implements Serializable {
    private long inquiryComment;
    private long member;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InquiryCommentLikeId)) return false;
        InquiryCommentLikeId that = (InquiryCommentLikeId) o;
        return inquiryComment == that.inquiryComment && member == that.member;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (int) (inquiryComment ^ (inquiryComment >>> 32));
        result = 31 * result + (int) (member ^ (member >>> 32));
        return result;
    }
}
