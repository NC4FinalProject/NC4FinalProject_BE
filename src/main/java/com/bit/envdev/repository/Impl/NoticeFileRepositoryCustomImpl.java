package com.bit.envdev.repository.Impl;

import com.bit.envdev.entity.NoticeFile;
import com.bit.envdev.repository.NoticeFileRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class NoticeFileRepositoryCustomImpl implements NoticeFileRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private final EntityManager entityManager;

    @Override
    @Transactional
    public void remove(NoticeFile noticeFile) {
        entityManager.remove(noticeFile);
    }

    @Override
    public void removeAll(List<NoticeFile> noticeFileList) {
        System.out.println(noticeFileList.size());
        noticeFileList.forEach(entityManager::remove);
    }
}
