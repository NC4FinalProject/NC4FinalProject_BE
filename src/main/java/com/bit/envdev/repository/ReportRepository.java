package com.bit.envdev.repository;

import com.bit.envdev.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {
    @Query(value = "SELECT r, r.refId FROM Report r WHERE r.refType = 'MEM' GROUP BY r.refId HAVING COUNT(r) >= 5 ORDER BY r.reportDate DESC LIMIT 10")
    List<Object[]> reportMember();

    @Modifying(clearAutomatically = true)
    @Query(value = "update BlockInquiry b set b.state=:code where b.reportId=:id")
    void updateSate(Long id, int code);
}
