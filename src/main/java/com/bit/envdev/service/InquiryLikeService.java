package com.bit.envdev.service;

import com.bit.envdev.dto.InquiryDTO;
import com.bit.envdev.entity.Member;

public interface InquiryLikeService {

    long addOrdown(long memberId, Long inquiryId);

    long findByInquiryId(Long inquiryId);

    InquiryDTO insertLike(Member member, Long inquiryId);

    long findByMemberIdAndInquiryId(long memberId, long inquiryId);

    InquiryDTO deleteLike(Member member, long inquiryId);
}
