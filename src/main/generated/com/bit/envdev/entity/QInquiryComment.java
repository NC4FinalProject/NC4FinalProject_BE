package com.bit.envdev.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QInquiryComment is a Querydsl query type for InquiryComment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QInquiryComment extends EntityPathBase<InquiryComment> {

    private static final long serialVersionUID = 1908525771L;

    public static final QInquiryComment inquiryComment = new QInquiryComment("inquiryComment");

    public final StringPath InquiryCommentContent = createString("InquiryCommentContent");

    public final DatePath<java.util.Date> inquiryCommentCrtDT = createDate("inquiryCommentCrtDT", java.util.Date.class);

    public final NumberPath<Long> inquiryCommentId = createNumber("inquiryCommentId", Long.class);

    public final DatePath<java.util.Date> inquiryCommentUdtDT = createDate("inquiryCommentUdtDT", java.util.Date.class);

    public QInquiryComment(String variable) {
        super(InquiryComment.class, forVariable(variable));
    }

    public QInquiryComment(Path<? extends InquiryComment> path) {
        super(path.getType(), path.getMetadata());
    }

    public QInquiryComment(PathMetadata metadata) {
        super(InquiryComment.class, metadata);
    }

}

