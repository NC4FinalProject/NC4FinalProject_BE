package com.bit.envdev.service;

import com.bit.envdev.dto.ContentsBookmarkDTO;
import com.bit.envdev.entity.Member;

import java.util.List;

public interface ContentsBookmarkService {

    List<ContentsBookmarkDTO> getBookmarkContents(long memberId);

    void addBookmark(ContentsBookmarkDTO contentsBookmarkDTO, Member member);

    void removeBookmark(int contentsId, long memberId);
}
