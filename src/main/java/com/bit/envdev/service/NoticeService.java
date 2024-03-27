package com.bit.envdev.service;

import com.bit.envdev.dto.NoticeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NoticeService {
    Page<NoticeDTO> searchAll(Pageable pageable, String searchCondition, String searchKeyword);

    void post(NoticeDTO noticeDTO);

    NoticeDTO findById(Long noticeNo);

    void removeImage(List<String> temporaryImage);
}
