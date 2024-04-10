package com.bit.envdev.service.impl;

import com.bit.envdev.entity.InquiryComment;
import com.bit.envdev.entity.InquiryCommentLike;
import com.bit.envdev.entity.Member;
import com.bit.envdev.repository.InquiryCommentLikeRepository;
import com.bit.envdev.service.InquiryCommentLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InquiryCommentLikeServiceImpl implements InquiryCommentLikeService {
    private final InquiryCommentLikeRepository inquiryCommentLikeRepository;
    @Override
    public long addOrdown(long memberId, Long inquiryCommentId) {
        return inquiryCommentLikeRepository.countByMemberMemberIdAndInquiryCommentInquiryCommentId(memberId, inquiryCommentId);
    }

    @Override
    public long findByInquiryCommentId(Long inquiryCommentId) {
        return inquiryCommentLikeRepository.countByInquiryCommentInquiryCommentId(inquiryCommentId);
    }

    @Override
    public void insertLike(Member member, Long inquiryCommentId) {

        InquiryCommentLike likeCnt = InquiryCommentLike.builder()
                .inquiryComment(InquiryComment.builder().inquiryCommentId(inquiryCommentId).build())
                .member(member)
                .build();
        if (addOrdown(member.getMemberId(), inquiryCommentId) > 0) {
            inquiryCommentLikeRepository.delete(likeCnt);
            return;
        }
        inquiryCommentLikeRepository.save(likeCnt);
    }
}
