package com.bit.envdev.service.impl;

import com.bit.envdev.dto.ContentsDTO;
import com.bit.envdev.entity.Contents;
import com.bit.envdev.entity.ContentsBookmark;
import com.bit.envdev.entity.ContentsBookmarkId;
import com.bit.envdev.entity.Member;
import com.bit.envdev.repository.ContentsBookmarkRepository;
import com.bit.envdev.repository.ContentsRepository;
import com.bit.envdev.repository.MemberRepository;
import com.bit.envdev.service.ContentsBookmarkService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContentsBookmarkServiceImpl implements ContentsBookmarkService {

    private final ContentsBookmarkRepository contentsBookmarkRepository;
    private final ContentsRepository contentsRepository;
    private final MemberRepository memberRepository;

    @Override
    public int getBookmarkContents(int contentsId, long memberId) {
        int bookmarkCount = contentsBookmarkRepository.countByContentsIdAndMemberId(contentsId, memberId);

        return bookmarkCount;
    }

    @Transactional
    @Override
    public void addBookmark(int contentsId, long memberId) {
        Contents contents = contentsRepository.findById(contentsId).orElseThrow();
        Member member = memberRepository.findById(memberId).orElseThrow();

        ContentsBookmark contentsBookmark = ContentsBookmark.builder()
                .contents(contents)
                .member(member)
                .build();

        contentsBookmarkRepository.save(contentsBookmark);

        contentsBookmarkRepository.flush();
    }

    @Transactional
    @Override
    public void removeBookmark(int contentsId, long memberId) {
        ContentsBookmarkId contentsBookmarkId = new ContentsBookmarkId();

        contentsBookmarkId.setContents(contentsId);
        contentsBookmarkId.setMember(memberId);

        contentsBookmarkRepository.deleteById(contentsBookmarkId);
        contentsBookmarkRepository.flush();

    }

}