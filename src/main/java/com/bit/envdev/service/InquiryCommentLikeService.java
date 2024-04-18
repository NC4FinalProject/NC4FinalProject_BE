package com.bit.envdev.service;

import com.bit.envdev.dto.InquiryCommentDTO;
import com.bit.envdev.entity.Member;

import java.util.List;

public interface InquiryCommentLikeService {

    long addOrdown(long memberId, Long inquiryCommentId);

    long findByInquiryCommentId(Long inquiryCommentId);

    List<InquiryCommentDTO> insertLike(Member member, Long inquiryCommentId);
}
