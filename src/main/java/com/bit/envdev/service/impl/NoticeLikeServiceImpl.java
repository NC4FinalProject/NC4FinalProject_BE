package com.bit.envdev.service.impl;

import com.bit.envdev.entity.Member;
import com.bit.envdev.entity.Notice;
import com.bit.envdev.entity.NoticeLike;
import com.bit.envdev.repository.NoticeLikeRepository;
import com.bit.envdev.repository.NoticeRepository;
import com.bit.envdev.service.NoticeLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoticeLikeServiceImpl implements NoticeLikeService {
    private final NoticeLikeRepository noticeLikeRepository;
    private final NoticeRepository noticeRepository;

    @Override
    public long addOrdown(long id, Long noticeNo) {
        return noticeLikeRepository.countByMemberMemberIdAndNoticeId(id, noticeNo);
    }

    @Override
    public long findByNoticeId(Long noticeNo) {

        return noticeLikeRepository.countByNoticeId(noticeNo);
    }

    public void insertLike(Member member, Long noticeNo) {

        NoticeLike likeCnt = NoticeLike.builder()
                .notice(Notice.builder().id(noticeNo).build())
                .member(member)
                .build();
        if (addOrdown(member.getMemberId(), noticeNo) > 0) {
            noticeLikeRepository.delete(likeCnt);
            return;
        }
        noticeLikeRepository.save(likeCnt);
    }
}
