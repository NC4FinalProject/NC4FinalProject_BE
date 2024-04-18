package com.bit.envdev.repository;

import com.bit.envdev.entity.InquiryLike;
import com.bit.envdev.entity.InquiryLikeId;
import com.bit.envdev.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InquiryLikeRepository extends JpaRepository<InquiryLike, InquiryLikeId>{
    long countByMemberMemberIdAndInquiryInquiryId(long memberId, Long inquiryId);

    long countByInquiryInquiryId(Long inquiryId);

    long countByMember(Member entity);
}
