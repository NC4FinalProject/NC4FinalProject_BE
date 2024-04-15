package com.bit.envdev.repository;

import com.bit.envdev.entity.InquiryCommentLike;
import com.bit.envdev.entity.InquiryCommentLikeId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface InquiryCommentLikeRepository extends JpaRepository<InquiryCommentLike, InquiryCommentLikeId>{
    long countByMemberMemberIdAndInquiryCommentInquiryCommentId(long memberId, Long inquiryCommentId);

    long countByInquiryCommentInquiryCommentId(Long inquiryCommentId);
}
