package com.bit.envdev.repository.Impl;

import com.bit.envdev.entity.Notice;
import com.bit.envdev.repository.NoticeRepositoryCustom;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.bit.envdev.entity.QNotice.notice;

public class NoticeRepositoryCustomImpl implements NoticeRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    public NoticeRepositoryCustomImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Page<Notice> searchAll(Pageable pageable, String searchCondition, String searchKeyword) {
        List<Notice> noticeList = jpaQueryFactory
                .selectFrom(notice)
                .where(getSearch(searchCondition, searchKeyword))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long totalCnt = jpaQueryFactory
                .select(notice.count())
                .where(getSearch(searchCondition, searchKeyword))
                .from(notice)
                .fetchOne();
        return new PageImpl<>(noticeList, pageable, totalCnt);
    }


    private BooleanBuilder getSearch(String searchCondition, String searchKeyword) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if(searchKeyword == null || searchKeyword.isEmpty()) {
            return null;
        }

        if(searchCondition.equalsIgnoreCase("all")) {
            booleanBuilder.or(notice.noticeTitle.containsIgnoreCase(searchKeyword));
            booleanBuilder.or(notice.noticeContent.containsIgnoreCase(searchKeyword));
            booleanBuilder.or(notice.noticeWriter.containsIgnoreCase(searchKeyword));
        } else if(searchCondition.equalsIgnoreCase("title")) {
            booleanBuilder.or(notice.noticeTitle.containsIgnoreCase(searchKeyword));
        } else if(searchCondition.equalsIgnoreCase("content")) {
            booleanBuilder.or(notice.noticeContent.containsIgnoreCase(searchKeyword));
        } else if(searchCondition.equalsIgnoreCase("writer")) {
            booleanBuilder.or(notice.noticeWriter.containsIgnoreCase(searchKeyword));
        }

        return booleanBuilder;
    }
}