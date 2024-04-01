package com.bit.envdev.contentsEntity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QContents is a Querydsl query type for Contents
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QContents extends EntityPathBase<Contents> {

    private static final long serialVersionUID = -844847341L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QContents contents = new QContents("contents");

    public final NumberPath<Integer> contentsId = createNumber("contentsId", Integer.class);

    public final StringPath contentsTitle = createString("contentsTitle");

    public final com.bit.envdev.entity.QMember member;

    public QContents(String variable) {
        this(Contents.class, forVariable(variable), INITS);
    }

    public QContents(Path<? extends Contents> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QContents(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QContents(PathMetadata metadata, PathInits inits) {
        this(Contents.class, metadata, inits);
    }

    public QContents(Class<? extends Contents> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.bit.envdev.entity.QMember(forProperty("member")) : null;
    }

}

