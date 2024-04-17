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

@Repository
@RequiredArgsConstructor
public class ContentsRepositoryCustomImpl implements ContentsRepositoryCustom {

}
