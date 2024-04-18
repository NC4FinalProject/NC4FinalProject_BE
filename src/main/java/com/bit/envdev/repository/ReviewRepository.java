package com.bit.envdev.repository;

import com.bit.envdev.entity.Member;
import com.bit.envdev.entity.Review;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

@Transactional
public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewRepositoryCustom {
    List<Review> findByContentsIdOrderByReviewUdtDateDesc(int contentsId);

    Optional<Review> findByMemberAndContentsId(Member member, int contentsId);

        @Query("SELECT c.contentsTitle, m.role, m.userNickname, r.reviewContent, r.reviewCrtDate " +
                "FROM Review r " +
                "JOIN r.member m " +
                "JOIN Contents c ON r.contentsId = c.contentsId " +
                "ORDER BY r.reviewCrtDate DESC")
        List<Object[]> findTop10ByMemberIdAndCreateAtDesc();

    @Query("SELECT c.contentsId, c.contentsTitle, c.thumbnail, c.category, c.member.memberId, c.price, m.userNickname, COUNT(r.reviewId) AS reviewCount, AVG(r.reviewRating) AS avgRating " +
            "FROM Contents c " +
            "JOIN Review r ON c.contentsId = r.contentsId " +
            "JOIN Member m ON c.member.memberId = m.memberId " +
            "GROUP BY c.contentsId, c.contentsTitle, c.thumbnail, c.category, c.member.memberId, m.userNickname, c.price " +
            "ORDER BY avgRating DESC, reviewCount DESC " +
            "LIMIT 12")
    List<Object[]> findTop12ContentsWithMemberInfoByRatingAndReviewCount();

    @Query("SELECT c.contentsId, c.contentsTitle, c.thumbnail, c.category, c.member.memberId, c.price, m.userNickname, COALESCE(COUNT(r.reviewId), 0) AS reviewCount, COALESCE(AVG(r.reviewRating), 0) AS avgRating " +
            "FROM Contents c " +
            "LEFT JOIN Review r ON c.contentsId = r.contentsId " +
            "JOIN Member m ON c.member.memberId = m.memberId " +
            "GROUP BY c.contentsId, c.contentsTitle, c.thumbnail, c.category, c.member.memberId, m.userNickname, c.price " +
            "ORDER BY c.regDate DESC " +
            "LIMIT 12")
    List<Object[]> findTopRecent12ContentsWithMemberInfoByRatingAndReviewCount();

    @Query("SELECT c.contentsId, c.contentsTitle, c.thumbnail, c.category, c.member.memberId, c.price, m.userNickname, COALESCE(COUNT(r.reviewId), 0) AS reviewCount, COALESCE(AVG(r.reviewRating), 0) AS avgRating " +
            "FROM Contents c " +
            "LEFT JOIN Review r ON c.contentsId = r.contentsId " +
            "JOIN Member m ON c.member.memberId = m.memberId " +
            "GROUP BY c.contentsId, c.contentsTitle, c.thumbnail, c.category, c.member.memberId, m.userNickname, c.price " +
            "ORDER BY RAND() " +
            "LIMIT 12")
    List<Object[]> findTopRandom12ContentsWithMemberInfoByRatingAndReviewCount();

    long countByMember(Member entity);
}

