package com.bit.envdev.service.impl;

import com.bit.envdev.dto.InquiryCommentDTO;
import com.bit.envdev.entity.CustomUserDetails;
import com.bit.envdev.repository.InquiryCommentRepository;
import com.bit.envdev.repository.MemberRepository;
import com.bit.envdev.service.InquiryCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class InquiryCommentServiceImpl implements InquiryCommentService {
    private final InquiryCommentRepository inquiryCommentRepository;
    private final MemberRepository memberRepository;

    @Override
    public List<InquiryCommentDTO> post(InquiryCommentDTO inquiryCommentDTO, CustomUserDetails customUserDetails) {

        inquiryCommentDTO.setMemberDTO(memberRepository.findById(customUserDetails.getMember().getMemberId()).orElseThrow().toDTO());
        inquiryCommentRepository.save(inquiryCommentDTO.toEntity());
        return inquiryCommentRepository.findByInquiryIdOrderByInquiryCommentUdtDateDesc(inquiryCommentDTO.getInquiryCommentId()).stream()
                .map(inquiryComment -> {
                    InquiryCommentDTO dto = inquiryComment.toDTO();
                    dto.getMemberDTO().setPassword("");
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<InquiryCommentDTO> modify(InquiryCommentDTO inquiryCommentDTO, CustomUserDetails customUserDetails) {
        return null;
    }

    @Override
    public List<InquiryCommentDTO> delete(long inquiryCommentId, CustomUserDetails customUserDetails) {
        return null;
    }

    @Override
    public List<InquiryCommentDTO> getInquiryCommentList(int inquiryCommentId) {
        return null;
    }


}
