package com.bit.envdev.repository.Impl;

import com.bit.envdev.repository.PointRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.bit.envdev.entity.QPoint.point;

@Repository
@RequiredArgsConstructor
public class PointRepositoryCustomImpl implements PointRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public long getMyPoint(long memberId) {
        long myPoint = jpaQueryFactory
                .select(point.value.sum())
                .from(point)
                .where(point.member.memberId.eq(memberId))
                .orderBy(point.createdAt.desc())
                .fetchOne();

        return myPoint;
    }
}
