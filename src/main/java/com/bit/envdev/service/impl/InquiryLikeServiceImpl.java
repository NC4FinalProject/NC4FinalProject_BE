package com.bit.envdev.service.impl;

import com.bit.envdev.dto.InquiryDTO;
import com.bit.envdev.entity.Inquiry;
import com.bit.envdev.entity.InquiryLike;
import com.bit.envdev.entity.Member;
import com.bit.envdev.repository.InquiryLikeRepository;
import com.bit.envdev.repository.InquiryRepository;
import com.bit.envdev.service.InquiryLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InquiryLikeServiceImpl implements InquiryLikeService {
    private final InquiryLikeRepository inquiryLikeRepository;
    private final InquiryRepository inquiryRepository;

    @Override
    public long addOrdown(long memberId, Long inquiryId) {
        return inquiryLikeRepository.countByMemberMemberIdAndInquiryInquiryId(memberId, inquiryId);
    }

    @Override
    public long findByInquiryId(Long inquiryId) {
        return inquiryLikeRepository.countByInquiryInquiryId(inquiryId);
    }

    @Override
    public InquiryDTO insertLike(Member member, Long inquiryId) {
        inquiryLikeRepository.save(InquiryLike.builder()
                .inquiry(inquiryRepository.findById(inquiryId).orElseThrow())
                .member(member)
                .build());

        return inquiryRepository.findById(inquiryId).orElseThrow().toDTO();
    }

    @Override
    public long findByMemberIdAndInquiryId(long memberId, long inquiryId) {
        return inquiryLikeRepository.countByMemberMemberIdAndInquiryInquiryId(memberId, inquiryId);
    }

    @Override
    public InquiryDTO deleteLike(Member member, long inquiryId) {
        inquiryLikeRepository.delete(InquiryLike.builder()
                .inquiry(inquiryRepository.findById(inquiryId).orElseThrow())
                .member(member)
                .build());

        return inquiryRepository.findById(inquiryId).orElseThrow().toDTO();
    }
}
