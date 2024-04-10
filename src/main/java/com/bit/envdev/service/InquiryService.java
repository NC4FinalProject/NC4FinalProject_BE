package com.bit.envdev.service;

import com.bit.envdev.dto.InquiryDTO;
import com.bit.envdev.dto.NoticeDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface InquiryService {
    Page<InquiryDTO> searchAll(Pageable pageable, String searchCondition, String searchKeyword);

    void post(InquiryDTO inquiryDTO);

    void removeImage(List<String> temporaryImage);

    void deleteById(Long inquiryId);

    void modify(InquiryDTO inquiryDTO);

    List<Long> modifyInquiryFileList(InquiryDTO inquiryDTO);

    InquiryDTO findById(Long inquiryId);

    void updateView(Long inquiryId, HttpServletRequest request, HttpServletResponse response);

    void modifyInquiryFile(List<Long> modifyInquiryFileLIst);

}
