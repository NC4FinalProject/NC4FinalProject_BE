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
import java.util.Map;
import java.util.Optional;

import static com.bit.envdev.entity.QInquiry.inquiry;

public class InquiryRepositoryCustomImpl implements InquiryRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public InquiryRepositoryCustomImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Page<Inquiry> searchAllByContentsId(Pageable pageable, String searchCondition, String searchKeyword, int contentsId){
        List<Inquiry> inquiryList = jpaQueryFactory
                .selectFrom(inquiry)
                .where(inquiry.contentsId.eq(contentsId).and(getSearch(searchCondition, searchKeyword)))
                .orderBy(inquiry.inquiryUdtDT.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long totalCnt = jpaQueryFactory
                .select(inquiry.count())
                .where(inquiry.contentsId.eq(contentsId).and(getSearch(searchCondition, searchKeyword)))
                .from(inquiry)
                .fetchOne();
        return new PageImpl<>(inquiryList, pageable, totalCnt);
    }

    @Override
    public Page<Inquiry> myInquiries(Pageable pageable, String searchCondition, String searchKeyword, int contentsId, long memberId) {
        List<Inquiry> inquiryList = jpaQueryFactory.selectFrom(inquiry)
                .where(inquiry.contentsId.eq(contentsId).and(inquiry.member.memberId.eq(memberId)).and(getSearch(searchCondition, searchKeyword)))
                .orderBy(inquiry.inquiryUdtDT.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long totalCnt = jpaQueryFactory
                .select(inquiry.count())
                .where(inquiry.contentsId.eq(contentsId).and(inquiry.member.memberId.eq(memberId)).and(getSearch(searchCondition, searchKeyword)))
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
