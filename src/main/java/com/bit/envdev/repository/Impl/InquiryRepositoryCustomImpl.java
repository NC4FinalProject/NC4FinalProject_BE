package com.bit.envdev.repository.Impl;

import com.bit.envdev.entity.Inquiry;
import com.bit.envdev.entity.Notice;
import com.bit.envdev.repository.InquiryRepository;
import com.bit.envdev.repository.InquiryRepositoryCustom;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.bit.envdev.entity.QInquiry.inquiry;

public class InquiryRepositoryCustomImpl implements InquiryRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public InquiryRepositoryCustomImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Page<Inquiry> searchAll(Pageable pageable, String searchCondition, String searchKeyword) {
        List<Inquiry> inquiryList = jpaQueryFactory
                .selectFrom(inquiry)
                .where(getSearch(searchCondition, searchKeyword))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long totalCnt = jpaQueryFactory
                .select(inquiry.count())
                .where(getSearch(searchCondition, searchKeyword))
                .from(inquiry)
                .fetchOne();
        return new PageImpl<>(inquiryList, pageable, totalCnt);
    }


    private BooleanBuilder getSearch(String searchCondition, String searchKeyword) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (searchKeyword == null || searchKeyword.isEmpty()) {
            return null;
        }

        if (searchCondition.equalsIgnoreCase("all")) {
            booleanBuilder.or(inquiry.inquiryTitle.containsIgnoreCase(searchKeyword));
            booleanBuilder.or(inquiry.inquiryContent.containsIgnoreCase(searchKeyword));
            booleanBuilder.or(inquiry.member.userNickname.containsIgnoreCase(searchKeyword));
            booleanBuilder.or(inquiry.tagList.any().tagContent.containsIgnoreCase(searchKeyword));
        } else if (searchCondition.equalsIgnoreCase("title")) {
            booleanBuilder.or(inquiry.inquiryTitle.containsIgnoreCase(searchKeyword));
        } else if (searchCondition.equalsIgnoreCase("content")) {
            booleanBuilder.or(inquiry.inquiryContent.containsIgnoreCase(searchKeyword));
        } else if (searchCondition.equalsIgnoreCase("writer")) {
            booleanBuilder.or(inquiry.member.userNickname.containsIgnoreCase(searchKeyword));
        } else if (searchCondition.equalsIgnoreCase("tag")) {
            booleanBuilder.or(inquiry.tagList.any().tagContent.containsIgnoreCase(searchKeyword));
        }

        return booleanBuilder;
    }
}
