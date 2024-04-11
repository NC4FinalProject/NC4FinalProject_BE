package com.bit.envdev.repository;

import com.bit.envdev.entity.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface InquiryRepository extends JpaRepository<Inquiry, Long>, InquiryRepositoryCustom{

    @Modifying
    @Query("update Inquiry i set i.inquiryView = i.inquiryId + 1 where i.inquiryId = :inquiryId")
    void updateView(Long inquiryId);

}
