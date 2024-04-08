package com.bit.envdev.repository;

import com.bit.envdev.entity.Notice;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Transactional
public interface NoticeRepository extends JpaRepository<Notice, Long>, NoticeRepositoryCustom{
    @Modifying
    @Query("update Notice n set n.view = n.view + 1 where n.id = :noticeNo")
    void updateView(Long noticeNo);

    List<Notice> findTop4ByOrderByNoticeDateDesc();
}
