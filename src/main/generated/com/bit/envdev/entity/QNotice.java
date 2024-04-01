package com.bit.envdev.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNotice is a Querydsl query type for Notice
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNotice extends EntityPathBase<Notice> {

    private static final long serialVersionUID = -361986549L;

    public static final QNotice notice = new QNotice("notice");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath noticeContent = createString("noticeContent");

    public final DateTimePath<java.time.LocalDateTime> noticeDate = createDateTime("noticeDate", java.time.LocalDateTime.class);

    public final ListPath<NoticeFile, QNoticeFile> noticeFileList = this.<NoticeFile, QNoticeFile>createList("noticeFileList", NoticeFile.class, QNoticeFile.class, PathInits.DIRECT2);

    public final StringPath noticeTitle = createString("noticeTitle");

    public final StringPath noticeWriter = createString("noticeWriter");

    public final NumberPath<Integer> view = createNumber("view", Integer.class);

    public QNotice(String variable) {
        super(Notice.class, forVariable(variable));
    }

    public QNotice(Path<? extends Notice> path) {
        super(path.getType(), path.getMetadata());
    }

    public QNotice(PathMetadata metadata) {
        super(Notice.class, metadata);
    }

}

