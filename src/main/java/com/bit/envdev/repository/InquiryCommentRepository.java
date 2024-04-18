package com.bit.envdev.repository;

import com.bit.envdev.entity.InquiryComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Repository
public interface InquiryCommentRepository extends JpaRepository<InquiryComment, Long> {

    List<InquiryComment> findByInquiryInquiryIdOrderByInquiryCommentCrtDTDesc(long inquiryId);

    long countByInquiryInquiryId(long inquiryId);

    @Query(value = "SELECT A.INQUIRY_COMMENT_ID\n" +
            "     , A.INQUIRY_ID\n" +
            "     , A.INQUIRY_COMMENT_CRTDT\n" +
            "     , A.INQUIRY_COMMENT_UDTDT\n" +
            "     , A.INQUIRY_COMMENT_CONTENT\n" +
            "     , A.MEMBER_ID\n" +
            "    FROM INQUIRY_COMMENT A\n" +
            "    LEFT JOIN (\n" +
            "        SELECT B.INQUIRY_COMMENT_ID\n" +
            "             , COUNT(B.INQUIRY_COMMENT_ID) AS LIKE_COUNT\n" +
            "            FROM INQUIRY_COMMENT_LIKE B\n" +
            "            GROUP BY B.INQUIRY_COMMENT_ID\n" +
            "    ) C\n" +
            "    ON A.INQUIRY_COMMENT_ID = C.INQUIRY_COMMENT_ID\n" +
            "    WHERE A.INQUIRY_ID = :inquiryId" +
            "    ORDER BY C.LIKE_COUNT DESC", nativeQuery = true)
    List<Map<String, Object>> findByInquiryInquiryIdOrderByInquiryCommentLikCountDesc(Long inquiryId);
}
