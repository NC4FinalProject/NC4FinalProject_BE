package com.bit.envdev.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class InquiryLikeId implements Serializable {
    private long inquiry;
    private long member;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InquiryLikeId)) return false;
        InquiryLikeId that = (InquiryLikeId) o;
        return inquiry == that.inquiry && member == that.member;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (int) (inquiry ^ (inquiry >>> 32));
        result = 31 * result + (int) (member ^ (member >>> 32));
        return result;
    }
}


