package com.bit.envdev.service.impl;

import com.bit.envdev.dto.InquiryCommentDTO;
import com.bit.envdev.entity.InquiryComment;
import com.bit.envdev.entity.InquiryCommentLike;
import com.bit.envdev.entity.Member;
import com.bit.envdev.repository.InquiryCommentLikeRepository;
import com.bit.envdev.repository.InquiryCommentRepository;
import com.bit.envdev.service.InquiryCommentLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InquiryCommentLikeServiceImpl implements InquiryCommentLikeService {
    private final InquiryCommentLikeRepository inquiryCommentLikeRepository;
    private final InquiryCommentRepository inquiryCommentRepository;

    @Override
    public long addOrdown(long memberId, Long inquiryCommentId) {
        return inquiryCommentLikeRepository.countByMemberMemberIdAndInquiryCommentInquiryCommentId(memberId, inquiryCommentId);
    }

    @Override
    public long findByInquiryCommentId(Long inquiryCommentId) {
        return inquiryCommentLikeRepository.countByInquiryCommentInquiryCommentId(inquiryCommentId);
    }

    @Override
    public List<InquiryCommentDTO> insertLike(Member member, Long inquiryCommentId) {

        InquiryCommentLike likeCnt = InquiryCommentLike.builder()
                .inquiryComment(InquiryComment.builder().inquiryCommentId(inquiryCommentId).build())
                .member(member)
                .build();
        if (addOrdown(member.getMemberId(), inquiryCommentId) > 0) {
            inquiryCommentLikeRepository.delete(likeCnt);
        } else {
            inquiryCommentLikeRepository.save(likeCnt);
        }

        InquiryComment sInquiryComment = inquiryCommentRepository.findById(inquiryCommentId).orElseThrow();

        return inquiryCommentRepository.findByInquiryInquiryIdOrderByInquiryCommentCrtDTDesc(sInquiryComment.getInquiry().getInquiryId()).stream()
                .map(inquiryComment -> {
                    InquiryCommentDTO dto = inquiryComment.toDTO();
                    dto.setInquiryCommentLikeCount(inquiryCommentLikeRepository.countByInquiryCommentInquiryCommentId(dto.getInquiryCommentId()));
                    long inquiryCommentLike = inquiryCommentLikeRepository.countByMemberMemberIdAndInquiryCommentInquiryCommentId(member.getMemberId(), inquiryCommentId);
                    if(inquiryCommentLike == 0) {
                        dto.setCommentLike(false);
                    } else {
                        dto.setCommentLike(true);
                    }

                    dto.getMemberDTO().setPassword("");
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
