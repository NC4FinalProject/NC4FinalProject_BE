package com.bit.envdev.repository;

import com.bit.envdev.entity.InquiryLike;
import com.bit.envdev.entity.InquiryLikeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InquiryLikeRepository extends JpaRepository<InquiryLike, InquiryLikeId>{
    long countByMemberMemberIdAndInquiryId(long memberId, Long inquiryId);

    long countByInquiryId(Long inquiryId);
}
