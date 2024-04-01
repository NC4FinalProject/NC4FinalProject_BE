package com.bit.envdev.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNoticeFile is a Querydsl query type for NoticeFile
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNoticeFile extends EntityPathBase<NoticeFile> {

    private static final long serialVersionUID = 896922151L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNoticeFile noticeFile = new QNoticeFile("noticeFile");

    public final NumberPath<Long> itemFileId = createNumber("itemFileId", Long.class);

    public final StringPath itemFileName = createString("itemFileName");

    public final StringPath itemFileOrigin = createString("itemFileOrigin");

    public final StringPath itemFilePath = createString("itemFilePath");

    public final QNotice notice;

    public QNoticeFile(String variable) {
        this(NoticeFile.class, forVariable(variable), INITS);
    }

    public QNoticeFile(Path<? extends NoticeFile> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNoticeFile(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNoticeFile(PathMetadata metadata, PathInits inits) {
        this(NoticeFile.class, metadata, inits);
    }

    public QNoticeFile(Class<? extends NoticeFile> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.notice = inits.isInitialized("notice") ? new QNotice(forProperty("notice")) : null;
    }

}

