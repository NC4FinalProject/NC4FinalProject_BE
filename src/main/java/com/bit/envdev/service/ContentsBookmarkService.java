package com.bit.envdev.service;

import com.bit.envdev.dto.ContentsDTO;

public interface ContentsBookmarkService {

    int getBookmarkContents(int contentsId, long memberId);

    void addBookmark(int contentsId, long memberId);

    void removeBookmark(int contentsId, long memberId);
}
