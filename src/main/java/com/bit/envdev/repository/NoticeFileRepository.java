package com.bit.envdev.repository;

import com.bit.envdev.entity.NoticeFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface NoticeFileRepository extends JpaRepository<NoticeFile, Long>, NoticeFileRepositoryCustom {
}
