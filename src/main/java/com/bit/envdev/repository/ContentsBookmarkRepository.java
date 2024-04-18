package com.bit.envdev.repository;

import com.bit.envdev.entity.ContentsBookmark;
import com.bit.envdev.entity.ContentsBookmarkId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface ContentsBookmarkRepository extends JpaRepository<ContentsBookmark, ContentsBookmarkId>, ContentsBookmarkRepositoryCustom {
    @Query("SELECT cb FROM ContentsBookmark cb WHERE cb.member.memberId = :memberId")
    List<ContentsBookmark> findAllByMemberId(long memberId);

    Optional<ContentsBookmark> findByContentsContentsIdAndMemberMemberId(int contentsId, long memberId);
}
