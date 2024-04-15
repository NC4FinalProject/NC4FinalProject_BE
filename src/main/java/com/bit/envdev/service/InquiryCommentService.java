package com.bit.envdev.service;

import com.bit.envdev.dto.InquiryCommentDTO;
import com.bit.envdev.entity.CustomUserDetails;

import java.util.List;

public interface InquiryCommentService {

    List<InquiryCommentDTO> post(long inquiryId, InquiryCommentDTO inquiryCommentDTO, CustomUserDetails customUserDetails);

    List<InquiryCommentDTO> modify(long inquiryId, InquiryCommentDTO inquiryCommentDTO, CustomUserDetails customUserDetails);
    List<InquiryCommentDTO> delete(long inquiryId, long inquiryCommentId, CustomUserDetails customUserDetails);

}
