package com.bit.envdev.repository;

import com.bit.envdev.entity.MemberGraph;
import com.bit.envdev.entity.MemberGraphId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MemberGraphRepository extends JpaRepository<MemberGraph, MemberGraphId> {
 @Query(value = "SELECT DATE(m.created_at) AS registration_date, COUNT(*) AS user_count " +
         "FROM Member m " +
         "WHERE m.created_at >= DATE_SUB(CURDATE(), INTERVAL 1 MONTH) " +
         "GROUP BY DATE(m.created_at)", nativeQuery = true)
 List<MemberGraph> findMemberGraph();

    @Query(value = "SELECT m1_0.created_at as registration_date, COUNT(*) as user_count FROM member m1_0 GROUP BY MONTH(m1_0.created_at), YEAR(m1_0.created_at)", nativeQuery = true)
    List<MemberGraph> findMonthTotalUserCount();

    @Query(value = "SELECT m.created_at as registration_date, COUNT(*) as user_count FROM member m WHERE m.created_at >= DATE_SUB(CURDATE(), INTERVAL 1 YEAR) GROUP BY MONTH(m.created_at), YEAR(m.created_at)", nativeQuery = true)
    List<MemberGraph> findMonthUserCount();

    @Query(value = "SELECT DATE(created_at) AS registration_date, COUNT(*) AS user_count " +
            "FROM member " +
            "WHERE role = 'RESIGNED' AND created_at >= DATE_SUB(NOW(), INTERVAL 1 MONTH) " +
            "GROUP BY DATE(created_at) " +
            "ORDER BY DATE(created_at)", nativeQuery = true)
     List<MemberGraph> findDailyOutUserCount();

     @Query(value = "SELECT DATE(created_at) AS registration_date, " +
             "       COUNT(*) AS user_count " +
             "FROM member " +
             "WHERE role = 'RESIGNED' " +
             "AND created_at >= DATE_SUB(CURDATE(), INTERVAL 1 YEAR) " +
             "GROUP BY YEAR(created_at), MONTH(created_at) " +
             "ORDER BY YEAR(created_at), MONTH(created_at)",
             nativeQuery = true)
     List<MemberGraph> findMonthlyOutUserCount();
}
