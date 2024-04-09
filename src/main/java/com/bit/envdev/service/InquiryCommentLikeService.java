package com.bit.envdev.service;

import com.bit.envdev.entity.Member;

public interface InquiryCommentLikeService {

    long addOrdown(long memberId, Long inquiryCommentId);

    long findByInquiryCommentId(Long inquiryCommentId);

    void insertLike(Member member, Long inquiryCommentId);
}
