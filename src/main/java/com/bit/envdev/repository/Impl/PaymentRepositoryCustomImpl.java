package com.bit.envdev.repository.Impl;

import com.bit.envdev.repository.PaymentRepositoryCustom;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.JpaQueryCreator;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bit.envdev.entity.QPayment.payment;
import static com.bit.envdev.entity.QPaymentContent.paymentContent;

@Repository
@RequiredArgsConstructor
public class PaymentRepositoryCustomImpl implements PaymentRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Map<String, Object>> getPurchaseList(Pageable pageable, long memberId) {
        List<Tuple> tuples = jpaQueryFactory
                .select(payment, paymentContent)
                .from(payment)
                .join(paymentContent)
                .on(payment.paymentId.eq(paymentContent.payment.paymentId))
                .where(payment.member.memberId.eq(memberId))
                .orderBy(payment.paymentDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long totalCnt = jpaQueryFactory
                .select(payment, paymentContent)
                .from(payment)
                .join(paymentContent)
                .on(payment.paymentId.eq(paymentContent.payment.paymentId))
                .where(payment.member.memberId.eq(memberId))
                .orderBy(payment.paymentDate.desc())
                .fetchCount();

        List<Map<String, Object>> mapList = new ArrayList<>();

        for(Tuple tuple : tuples) {
            Map<String, Object> map = new HashMap<>();

            map.put("paymentId", tuple.get(payment).getPaymentId());
            map.put("paymentDate", tuple.get(payment).getPaymentDate());
            map.put("memberId", tuple.get(payment).getMember().getMemberId());
            map.put("contentId", tuple.get(paymentContent).getContentsId());
            map.put("paymentContentId", tuple.get(paymentContent).getPaymentContentId());
            map.put("price", tuple.get(paymentContent).getPrice());
            map.put("contentsTitle", tuple.get(paymentContent).getContentsTitle());
            map.put("teacherName", tuple.get(paymentContent).getTeacherName());
            map.put("thumbnail", tuple.get(paymentContent).getThumbnail());

            mapList.add(map);
        }

        return new PageImpl<>(mapList, pageable, totalCnt);
    }
}
