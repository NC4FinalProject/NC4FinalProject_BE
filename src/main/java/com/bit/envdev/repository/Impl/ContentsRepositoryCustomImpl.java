package com.bit.envdev.repository.Impl;

import com.bit.envdev.entity.Contents;
import com.bit.envdev.repository.ContentsRepositoryCustom;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.bit.envdev.entity.QContents.contents;
import static com.bit.envdev.entity.QPaymentContent.paymentContent;
import static com.bit.envdev.entity.QPayment.payment;
import static com.bit.envdev.entity.QReview.review;

@Repository
@RequiredArgsConstructor
public class ContentsRepositoryCustomImpl implements ContentsRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Map<String, Object>> searchAllSearch(Pageable pageable, String category, String pricePattern, String orderType, String searchKeyword) {
        List<Tuple> tuples = jpaQueryFactory
                .select(contents.contentsId)

        return null;
    }
}
