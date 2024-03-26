package com.bit.envdev.service;

import com.bit.envdev.dto.NoticeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoticeService {
    Page<NoticeDTO> searchAll(Pageable pageable, String searchCondition, String searchKeyword);

    void post(NoticeDTO noticeDTO);

    NoticeDTO findById(Long noticeNo);
}
