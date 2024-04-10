package com.bit.envdev.repository;

import com.bit.envdev.entity.InquiryFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InquiryFileRepository extends JpaRepository<InquiryFile, Long>{
}
