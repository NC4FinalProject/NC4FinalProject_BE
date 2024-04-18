package com.bit.envdev.service.impl;

import com.bit.envdev.dto.InquiryCommentDTO;
import com.bit.envdev.entity.CustomUserDetails;
import com.bit.envdev.entity.Inquiry;
import com.bit.envdev.entity.InquiryComment;
import com.bit.envdev.repository.InquiryCommentLikeRepository;
import com.bit.envdev.repository.InquiryCommentRepository;
import com.bit.envdev.repository.InquiryRepository;
import com.bit.envdev.repository.MemberRepository;
import com.bit.envdev.service.InquiryCommentService;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class InquiryCommentServiceImpl implements InquiryCommentService {
    private final InquiryCommentRepository inquiryCommentRepository;
    private final MemberRepository memberRepository;
    private final InquiryRepository inquiryRepository;
    private final InquiryCommentLikeRepository inquiryCommentLikeRepository;

    @Override
    public List<InquiryCommentDTO> post(InquiryCommentDTO inquiryCommentDTO, CustomUserDetails customUserDetails) {
        Inquiry inquiry = inquiryRepository.findById(inquiryCommentDTO.getInquiryId())
                .orElseThrow(() -> new RuntimeException("해당 문의글이 존재하지 않습니다."));

        inquiryCommentDTO.setMemberDTO(memberRepository.findById(customUserDetails.getMember().getMemberId()).orElseThrow().toDTO());
        inquiryCommentRepository.save(inquiryCommentDTO.toEntity(inquiry));

        return inquiryCommentRepository.findByInquiryInquiryIdOrderByInquiryCommentCrtDTDesc(inquiryCommentDTO.getInquiryId()).stream()
                .map(inquiryComment -> {
                    InquiryCommentDTO dto = inquiryComment.toDTO();
                    dto.setInquiryCommentLikeCount(inquiryCommentLikeRepository.countByInquiryCommentInquiryCommentId(dto.getInquiryCommentId()));
                    long inquiryCommentLike = inquiryCommentLikeRepository.countByMemberMemberIdAndInquiryCommentInquiryCommentId(customUserDetails.getMember().getMemberId(), inquiryComment.getInquiryCommentId());
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

    @Override
    @Modifying
    public List<InquiryCommentDTO> modify(InquiryCommentDTO inquiryCommentDTO, CustomUserDetails customUserDetails) {
        InquiryComment inquiryComment = inquiryCommentRepository.findById(inquiryCommentDTO.getInquiryCommentId()).orElseThrow();

        InquiryComment modifiedInquiryComment = InquiryComment.builder()
                .inquiry(inquiryComment.getInquiry())
                .inquiryCommentId(inquiryCommentDTO.getInquiryCommentId())
                .inquiryCommentContent(inquiryCommentDTO.getInquiryCommentContent())
                .member(inquiryComment.getMember())
                .inquiryCommentCrtDT(inquiryComment.getInquiryCommentCrtDT())
                .inquiryCommentUdtDT(inquiryComment.getInquiryCommentUdtDT())
                .build();

        inquiryCommentRepository.save(modifiedInquiryComment);
        return inquiryCommentRepository.findByInquiryInquiryIdOrderByInquiryCommentCrtDTDesc(modifiedInquiryComment.getInquiry().getInquiryId()).stream()
                .map(inquiryCommen -> {
                    InquiryCommentDTO dto = inquiryCommen.toDTO();
                    dto.setInquiryCommentLikeCount(inquiryCommentLikeRepository.countByInquiryCommentInquiryCommentId(dto.getInquiryCommentId()));
                    long inquiryCommentLike = inquiryCommentLikeRepository.countByMemberMemberIdAndInquiryCommentInquiryCommentId(customUserDetails.getMember().getMemberId(), inquiryCommen.getInquiryCommentId());
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

    @Override
    public List<InquiryCommentDTO> delete(long inquiryId, long inquiryCommentId, CustomUserDetails customUserDetails) {

        inquiryCommentRepository.deleteById(inquiryCommentId);
        return inquiryCommentRepository.findByInquiryInquiryIdOrderByInquiryCommentCrtDTDesc(inquiryId).stream()
                .map(inquiryComment -> {
                    InquiryCommentDTO dto = inquiryComment.toDTO();
                    dto.setInquiryCommentLikeCount(inquiryCommentLikeRepository.countByInquiryCommentInquiryCommentId(dto.getInquiryCommentId()));
                    long inquiryCommentLike = inquiryCommentLikeRepository.countByMemberMemberIdAndInquiryCommentInquiryCommentId(customUserDetails.getMember().getMemberId(), inquiryCommentId);
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

    @Override
    public List<InquiryCommentDTO> getComments(Long inquiryId, String order, CustomUserDetails customUserDetails) {
        if(order.equalsIgnoreCase("좋아요순")) {
            List<Map<String, Object>> mapList = inquiryCommentRepository.findByInquiryInquiryIdOrderByInquiryCommentLikCountDesc(inquiryId);

            List<InquiryComment> inquiryCommentList = mapList.stream().map(map -> {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                format.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
                try {
                    Date crtDt = format.parse(map.get("INQUIRY_COMMENT_CRTDT").toString());
                    Date udtDt = format.parse(map.get("INQUIRY_COMMENT_UDTDT").toString());

                    Calendar crtCal = Calendar.getInstance();
                    Calendar udtCal = Calendar.getInstance();

                    crtCal.setTime(crtDt);
                    udtCal.setTime(udtDt);

                    crtCal.add(Calendar.HOUR_OF_DAY, 9);
                    udtCal.add(Calendar.HOUR_OF_DAY, 9);

                    return InquiryComment.builder()
                            .inquiryCommentId(Long.valueOf(map.get("INQUIRY_COMMENT_ID").toString()))
                            .inquiry(inquiryRepository.findById(Long.valueOf(map.get("INQUIRY_ID").toString())).orElseThrow())
                            .inquiryCommentContent(map.get("INQUIRY_COMMENT_CONTENT").toString())
                            .inquiryCommentCrtDT(new Date(crtCal.getTimeInMillis()))
                            .inquiryCommentUdtDT(new Date(udtCal.getTimeInMillis()))
                            .member(memberRepository.findById(Long.valueOf(map.get("MEMBER_ID").toString())).orElseThrow())
                        .build();
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }).toList();

            return inquiryCommentList.stream()
                    .map(inquiryComment -> {
                        InquiryCommentDTO dto = inquiryComment.toDTO();
                        dto.setInquiryCommentLikeCount(inquiryCommentLikeRepository.countByInquiryCommentInquiryCommentId(dto.getInquiryCommentId()));
                        long inquiryCommentLike = inquiryCommentLikeRepository.countByMemberMemberIdAndInquiryCommentInquiryCommentId(customUserDetails.getMember().getMemberId(), dto.getInquiryCommentId());
                        if(inquiryCommentLike == 0) {
                            dto.setCommentLike(false);
                        } else {
                            dto.setCommentLike(true);
                        }
                        dto.getMemberDTO().setPassword("");
                        return dto;
                    })
                    .collect(Collectors.toList());
        } else {
            return inquiryCommentRepository.findByInquiryInquiryIdOrderByInquiryCommentCrtDTDesc(inquiryId).stream()
                    .map(inquiryComment -> {
                        InquiryCommentDTO dto = inquiryComment.toDTO();
                        dto.setInquiryCommentLikeCount(inquiryCommentLikeRepository.countByInquiryCommentInquiryCommentId(dto.getInquiryCommentId()));
                        long inquiryCommentLike = inquiryCommentLikeRepository.countByMemberMemberIdAndInquiryCommentInquiryCommentId(customUserDetails.getMember().getMemberId(), dto.getInquiryCommentId());
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
}
