package com.bit.envdev.service.impl;

import com.bit.envdev.entity.Inquiry;
import com.bit.envdev.entity.InquiryLike;
import com.bit.envdev.entity.Member;
import com.bit.envdev.repository.InquiryLikeRepository;
import com.bit.envdev.service.InquiryLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InquiryLikeServiceImpl implements InquiryLikeService {
    private final InquiryLikeRepository inquiryLikeRepository;

    @Override
    public long addOrdown(long memberId, Long inquiryId) {
        return inquiryLikeRepository.countByMemberMemberIdAndInquiryId(memberId, inquiryId);
    }

    @Override
    public long findByInquiryId(Long inquiryId) {
        return inquiryLikeRepository.countByInquiryId(inquiryId);
    }

    @Override
    public void insertLike(Member member, Long inquiryId) {

        InquiryLike likeCnt = InquiryLike.builder()
                .inquiry(Inquiry.builder().inquiryId(inquiryId).build())
                .member(member)
                .build();
        if (addOrdown(member.getMemberId(), inquiryId) > 0) {
            inquiryLikeRepository.delete(likeCnt);
            return;
        }
        inquiryLikeRepository.save(likeCnt);
    }
}
