package com.bit.envdev.repository;

import com.bit.envdev.entity.MemberGraph;
import com.bit.envdev.entity.MemberGraphId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MemberGraphRepository extends JpaRepository<MemberGraph, MemberGraphId> {
   @Query(value = "select date(m1_0.created_at) as registration_date, count(*) as user_count from member m1_0 group by date(m1_0.created_at)", nativeQuery = true)
    List<MemberGraph> findMemberGraph();

    @Query(value = "SELECT t.registration_date, SUM(t.user_count) OVER (ORDER BY t.registration_date) as user_count FROM (SELECT DATE(m1_0.created_at) as registration_date, COUNT(*) as user_count FROM member m1_0 WHERE m1_0.created_at >= CURDATE() - INTERVAL 30 DAY GROUP BY DATE(m1_0.created_at)) t", nativeQuery = true)
    List<MemberGraph> findTotalUserCount();

    @Query(value = "SELECT m1_0.created_at as registration_date, COUNT(*) as user_count FROM member m1_0 GROUP BY MONTH(m1_0.created_at), YEAR(m1_0.created_at)", nativeQuery = true)
    List<MemberGraph> findMonthTotalUserCount();

    @Query(value = "SELECT m.created_at as registration_date, COUNT(*) as user_count FROM member m WHERE m.created_at >= DATE_SUB(CURDATE(), INTERVAL 1 YEAR) GROUP BY MONTH(m.created_at), YEAR(m.created_at)", nativeQuery = true)
    List<MemberGraph> findMonthUserCount();
}
