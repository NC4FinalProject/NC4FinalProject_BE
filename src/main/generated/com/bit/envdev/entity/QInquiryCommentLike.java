package com.bit.envdev.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QInquiryCommentLike is a Querydsl query type for InquiryCommentLike
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QInquiryCommentLike extends EntityPathBase<InquiryCommentLike> {

    private static final long serialVersionUID = -753037054L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QInquiryCommentLike inquiryCommentLike = new QInquiryCommentLike("inquiryCommentLike");

    public final QInquiryComment inquiryComment;

    public final NumberPath<Long> inquiryCommentLikeId = createNumber("inquiryCommentLikeId", Long.class);

    public QInquiryCommentLike(String variable) {
        this(InquiryCommentLike.class, forVariable(variable), INITS);
    }

    public QInquiryCommentLike(Path<? extends InquiryCommentLike> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QInquiryCommentLike(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QInquiryCommentLike(PathMetadata metadata, PathInits inits) {
        this(InquiryCommentLike.class, metadata, inits);
    }

    public QInquiryCommentLike(Class<? extends InquiryCommentLike> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.inquiryComment = inits.isInitialized("inquiryComment") ? new QInquiryComment(forProperty("inquiryComment")) : null;
    }

}

