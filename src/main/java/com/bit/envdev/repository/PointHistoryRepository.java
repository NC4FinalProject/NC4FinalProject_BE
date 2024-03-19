package com.bit.envdev.repository;


import com.bit.envdev.entity.PointHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PointHistoryRepository extends JpaRepository<PointHistory, Long> {

    @Transactional
    @Modifying
    @Query(value = "insert into point_history(point, username, point_category, point_history_modified_date) VALUES(:point, :username, :pointCategory , now())" , nativeQuery = true)
    void setPointHistory(int point, String username, String pointCategory);

    @Query(value = "select P from PointHistory P where P.username = :username ")
    List<PointHistory> getPointHistory(String username);
//    @Query(value = "select P from PointHistory P where P.username = :username ")
//    List<PointHistory> findByMemberUsername(String username);


}
