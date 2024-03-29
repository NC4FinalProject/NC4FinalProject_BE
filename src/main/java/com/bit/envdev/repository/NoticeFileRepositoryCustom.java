package com.bit.envdev.repository;

import com.bit.envdev.entity.NoticeFile;

import java.util.List;

public interface NoticeFileRepositoryCustom {
    void remove(NoticeFile noticeFile);

    void removeAll(List<NoticeFile> noticeFileList);
}
