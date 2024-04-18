package com.bit.envdev.repository;

public interface ContentsBookmarkRepositoryCustom {
    int countByContentsIdAndMemberId(int contentsId, long memberId);
}
