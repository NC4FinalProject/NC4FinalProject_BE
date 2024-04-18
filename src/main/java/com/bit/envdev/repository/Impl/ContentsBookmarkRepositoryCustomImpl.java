package com.bit.envdev.repository.Impl;

import com.bit.envdev.repository.ContentsBookmarkRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.bit.envdev.entity.QContentsBookmark.contentsBookmark;

@Repository
@RequiredArgsConstructor
public class ContentsBookmarkRepositoryCustomImpl implements ContentsBookmarkRepositoryCustom {
    private  final JPAQueryFactory jpaQueryFactory;

    @Override
    public int countByContentsIdAndMemberId(int contentsId, long memberId) {
        int bookmarkCount = jpaQueryFactory
                .select(contentsBookmark.count())
                .from(contentsBookmark)
                .where(contentsBookmark.contents.contentsId.eq(contentsId).and(contentsBookmark.member.memberId.eq(memberId)))
                .fetchOne().intValue();

        return bookmarkCount;
    }
}
