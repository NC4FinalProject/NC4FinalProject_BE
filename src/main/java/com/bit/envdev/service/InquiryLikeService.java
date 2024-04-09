package com.bit.envdev.service;

import com.bit.envdev.entity.Member;

public interface InquiryLikeService {

    long addOrdown(long memberId, Long inquiryId);

    long findByInquiryId(Long inquiryId);

    void insertLike(Member member, Long inquiryId);
}
