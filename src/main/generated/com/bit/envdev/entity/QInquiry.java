package com.bit.envdev.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QInquiry is a Querydsl query type for Inquiry
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QInquiry extends EntityPathBase<Inquiry> {

    private static final long serialVersionUID = 1489731828L;

    public static final QInquiry inquiry = new QInquiry("inquiry");

    public final DatePath<java.util.Date> inquiryCommentCrtDT = createDate("inquiryCommentCrtDT", java.util.Date.class);

    public final DatePath<java.util.Date> inquiryCommentUdtDT = createDate("inquiryCommentUdtDT", java.util.Date.class);

    public final StringPath inquiryContent = createString("inquiryContent");

    public final NumberPath<Long> inquiryId = createNumber("inquiryId", Long.class);

    public final StringPath inquiryTitle = createString("inquiryTitle");

    public final BooleanPath isPrivate = createBoolean("isPrivate");

    public final BooleanPath isSolved = createBoolean("isSolved");

    public final ListPath<Tag, QTag> tagMappingList = this.<Tag, QTag>createList("tagMappingList", Tag.class, QTag.class, PathInits.DIRECT2);

    public QInquiry(String variable) {
        super(Inquiry.class, forVariable(variable));
    }

    public QInquiry(Path<? extends Inquiry> path) {
        super(path.getType(), path.getMetadata());
    }

    public QInquiry(PathMetadata metadata) {
        super(Inquiry.class, metadata);
    }

}

