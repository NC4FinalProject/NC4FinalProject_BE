package com.bit.envdev.service;

import com.bit.envdev.dto.InquiryDTO;
import com.bit.envdev.entity.Member;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface InquiryService {
    Page<InquiryDTO> searchAll(Pageable pageable, String searchCondition, String searchKeyword, int contentsId);

    void post(InquiryDTO inquiryDTO, long memberId);

    void removeImage(List<String> temporaryImage);

    void deleteById(Long inquiryId);

    InquiryDTO modify(InquiryDTO inquiryDTO, long memberId);

    List<Long> modifyInquiryFileList(InquiryDTO inquiryDTO);

    InquiryDTO findById(Long inquiryId);

    void updateView(Long inquiryId, HttpServletRequest request, HttpServletResponse response);

    void modifyInquiryFile(List<Long> modifyInquiryFileLIst);

    InquiryDTO updateInquiryView(Long inquiryId);

    Page<InquiryDTO> myInquiries(Pageable pageable, String searchCondition, String searchKeyword, int contentsId, long memberId);

    InquiryDTO upadateSolve(long inquiryId);

    String getContentsTitle(int contentsId);

    String getContentsAuthor(int contentsId);

    long getLikeCount(long inquiryId);

    long countByMemberId(Member entity);
}
