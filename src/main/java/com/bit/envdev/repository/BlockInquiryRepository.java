package com.bit.envdev.repository;

import com.bit.envdev.entity.BlockInquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface BlockInquiryRepository extends JpaRepository<BlockInquiry, Long> {
    @Modifying(clearAutomatically = true)
    @Query(value = "update BlockInquiry b set b.state=1 where b.reportId=:id")
    void updateStateById(Long id);

    @Modifying(clearAutomatically = true)
    @Query(value = "update BlockInquiry b set b.state=1 where b.blockPeriod<:now")
    void updateStateByBlockPeriod(LocalDateTime now);
}
