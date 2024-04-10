package com.bit.envdev.service.impl;

import com.bit.envdev.dto.InquiryCommentDTO;
import com.bit.envdev.entity.CustomUserDetails;
import com.bit.envdev.entity.Inquiry;
import com.bit.envdev.repository.InquiryCommentRepository;
import com.bit.envdev.repository.InquiryRepository;
import com.bit.envdev.repository.MemberRepository;
import com.bit.envdev.service.InquiryCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class InquiryCommentServiceImpl implements InquiryCommentService {
    private final InquiryCommentRepository inquiryCommentRepository;
    private final MemberRepository memberRepository;
    private final InquiryRepository inquiryRepository;

    @Override
    public List<InquiryCommentDTO> post(long inquiryId, InquiryCommentDTO inquiryCommentDTO, CustomUserDetails customUserDetails) {

        if (!inquiryRepository.existsById(inquiryId)) {
            throw new RuntimeException("해당 문의글이 존재하지 않습니다.");
        }

        inquiryCommentDTO.setMemberDTO(memberRepository.findById(customUserDetails.getMember().getMemberId()).orElseThrow().toDTO());
        inquiryCommentRepository.save(inquiryCommentDTO.toEntity());
        return inquiryCommentRepository.findByInquiryInquiryIdOrderByInquiryCommentCrtDTDesc(inquiryId).stream()
                .map(inquiryComment -> {
                    InquiryCommentDTO dto = inquiryComment.toDTO();
                    dto.getMemberDTO().setPassword("");
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    @Modifying
    public List<InquiryCommentDTO> modify(long inquiryId, InquiryCommentDTO inquiryCommentDTO, CustomUserDetails customUserDetails) {
        if (!inquiryRepository.existsById(inquiryId)) {
            throw new RuntimeException("해당 문의글이 존재하지 않습니다.");
        }

        inquiryCommentDTO.setMemberDTO(memberRepository.findById(customUserDetails.getMember().getMemberId()).orElseThrow().toDTO());
        inquiryCommentRepository.save(inquiryCommentDTO.toEntity());
        return inquiryCommentRepository.findByInquiryInquiryIdOrderByInquiryCommentCrtDTDesc(inquiryId).stream()
                .map(inquiryComment -> {
                    InquiryCommentDTO dto = inquiryComment.toDTO();
                    dto.getMemberDTO().setPassword("");
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<InquiryCommentDTO> delete(long inquiryId, long inquiryCommentId, CustomUserDetails customUserDetails) {

        inquiryCommentRepository.deleteById(inquiryCommentId);
        return inquiryCommentRepository.findByInquiryInquiryIdOrderByInquiryCommentCrtDTDesc(inquiryId).stream()
                .map(inquiryComment -> {
                    InquiryCommentDTO dto = inquiryComment.toDTO();
                    dto.getMemberDTO().setPassword("");
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
