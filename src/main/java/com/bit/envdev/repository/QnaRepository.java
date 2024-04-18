package com.bit.envdev.repository;

import com.bit.envdev.entity.Member;
import com.bit.envdev.entity.Qna;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QnaRepository extends JpaRepository<Qna, Long> {
    @Query("SELECT q FROM Qna q WHERE q.answered = false ORDER BY q.createdAt DESC")
    List<Qna> get4Qna();

    long countByAnsweredFalse();

    @Query("SELECT q FROM Qna q WHERE q.askUser.userNickname LIKE %:searchKeyword% OR q.content LIKE %:searchKeyword% ORDER BY q.id DESC")
    Page<Qna> searchData(Pageable pageable, String searchKeyword);

    @Query("SELECT q FROM Qna q WHERE (q.askUser.userNickname LIKE %:searchKeyword% OR q.content LIKE %:searchKeyword%) AND (q.category LIKE %:searchCondition% OR q.content LIKE %:searchCondition%)ORDER BY q.id DESC")
    Page<Qna> findByMemberAndContentContaining(Pageable pageable, String searchCondition, String searchKeyword);
    Page<Qna> findByAskUserOrderByIdDesc(Pageable pageable, Member member);

    long countByAskUser(Member entity);
}
