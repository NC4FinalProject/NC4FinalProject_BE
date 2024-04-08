package com.bit.envdev.repository;

import com.bit.envdev.entity.NoticeLike;
import com.bit.envdev.entity.NoticeLikeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeLikeRepository extends JpaRepository<NoticeLike, NoticeLikeId> {

    long countByMemberMemberIdAndNoticeId(long memberId, Long noticeNo);

    long countByNoticeId(Long noticeNo);
}
