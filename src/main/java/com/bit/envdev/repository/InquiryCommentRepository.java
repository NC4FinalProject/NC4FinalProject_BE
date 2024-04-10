package com.bit.envdev.repository;

import com.bit.envdev.entity.InquiryComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InquiryCommentRepository extends JpaRepository<InquiryComment, Long> {

    List<InquiryComment> findByInquiryInquiryIdOrderByInquiryCommentCrtDTDesc(long inquiryId);
}
