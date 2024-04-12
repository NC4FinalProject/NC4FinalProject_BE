package com.bit.envdev.service.impl;

import com.bit.envdev.dto.ContentsBookmarkDTO;
import com.bit.envdev.entity.ContentsBookmark;
import com.bit.envdev.entity.Member;
import com.bit.envdev.repository.ContentsBookmarkRepository;
import com.bit.envdev.repository.ContentsRepository;
import com.bit.envdev.service.ContentsBookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContentsBookmarkServiceImpl implements ContentsBookmarkService {

    private final ContentsBookmarkRepository contentsBookmarkRepository;
    private final ContentsRepository contentsRepository;

    @Override
    public List<ContentsBookmarkDTO> getBookmarkContents(long memberId) {
        List<ContentsBookmark> bookmarkedContents = contentsBookmarkRepository.findAllByMemberId(memberId);
        System.out.println("5555555555555555555555555555");
        return bookmarkedContents.stream()
                .map(ContentsBookmark::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void addBookmark(ContentsBookmarkDTO contentsBookmarkDTO, Member member) {
        System.out.println(contentsBookmarkDTO.getContentsId());
        System.out.println("11111111111111111111111111111");
        Optional<ContentsBookmark> optionalContentsBookmark = contentsBookmarkRepository.findByContentsContentsIdAndMemberMemberId(contentsBookmarkDTO.getContentsId(), member.getMemberId());
        System.out.println("22222222222222222222222222222");
        if (optionalContentsBookmark.isPresent()) {
            throw new RuntimeException("already exist bookmark");
        }
        System.out.println("3333333333333333333333333333333");
        ContentsBookmark contentsBookmark = ContentsBookmark.builder()
                .contents(contentsRepository.findById(contentsBookmarkDTO.getContentsId()).orElseThrow())
                .member(member)
                .build();
        System.out.println("444444444444444444444444");
        contentsBookmarkRepository.save(contentsBookmark);
    }

    @Transactional
    @Override
    public void removeBookmark(int contentsId, long memberId) {
        Optional<ContentsBookmark> optionalContentsBookmark = contentsBookmarkRepository.findByContentsContentsIdAndMemberMemberId(contentsId, memberId);

        if (optionalContentsBookmark.isPresent()) {
            ContentsBookmark contentsBookmark = optionalContentsBookmark.get();
            contentsBookmarkRepository.delete(contentsBookmark);
        } else {
            throw new RuntimeException("Bookmark not found");
        }
    }

}