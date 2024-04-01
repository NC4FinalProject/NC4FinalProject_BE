package com.bit.envdev.repository;

import com.bit.envdev.entity.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoticeRepositoryCustom {
    Page<Notice> searchAll(Pageable pageable, String searchCondition, String searchKeyword);

}
