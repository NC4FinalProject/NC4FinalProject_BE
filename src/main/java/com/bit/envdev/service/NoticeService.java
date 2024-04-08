package com.bit.envdev.service;

import com.bit.envdev.dto.NoticeDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NoticeService {
    Page<NoticeDTO> searchAll(Pageable pageable, String searchCondition, String searchKeyword);

    void post(NoticeDTO noticeDTO);

    void removeImage(List<String> temporaryImage);

    void deleteById(Long noticeNo);

    void modify(NoticeDTO noticeDTO);

    List<Long> modifyNoticeFileList(NoticeDTO noticeDTO);

    NoticeDTO findById(Long noticeNo);

    void updateView(Long noticeNo, HttpServletRequest request, HttpServletResponse response);

    void modifyNoticeFile(List<Long> modifyNoticeFileLIst);

    List<NoticeDTO> findAll();
}
