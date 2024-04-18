package com.bit.envdev.service;

import com.bit.envdev.dto.InquiryCommentDTO;
import com.bit.envdev.entity.CustomUserDetails;

import java.util.List;

public interface InquiryCommentService {

    List<InquiryCommentDTO> post(InquiryCommentDTO inquiryCommentDTO, CustomUserDetails customUserDetails);

    List<InquiryCommentDTO> modify(InquiryCommentDTO inquiryCommentDTO, CustomUserDetails customUserDetails);
    List<InquiryCommentDTO> delete(long inquiryId, long inquiryCommentId, CustomUserDetails customUserDetails);

    List<InquiryCommentDTO> getComments(Long inquiryId, String order, CustomUserDetails customUserDetails);
}
