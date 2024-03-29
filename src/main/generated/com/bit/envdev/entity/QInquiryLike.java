package com.bit.envdev.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QInquiryLike is a Querydsl query type for InquiryLike
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QInquiryLike extends EntityPathBase<InquiryLike> {

    private static final long serialVersionUID = 345901739L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QInquiryLike inquiryLike = new QInquiryLike("inquiryLike");

    public final QInquiry inquiry;

    public final NumberPath<Long> inquiryLikeId = createNumber("inquiryLikeId", Long.class);

    public QInquiryLike(String variable) {
        this(InquiryLike.class, forVariable(variable), INITS);
    }

    public QInquiryLike(Path<? extends InquiryLike> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QInquiryLike(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QInquiryLike(PathMetadata metadata, PathInits inits) {
        this(InquiryLike.class, metadata, inits);
    }

    public QInquiryLike(Class<? extends InquiryLike> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.inquiry = inits.isInitialized("inquiry") ? new QInquiry(forProperty("inquiry")) : null;
    }

}

