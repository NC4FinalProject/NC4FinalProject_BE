package com.bit.envdev.repository;


import com.bit.envdev.entity.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface PointRepository extends JpaRepository<Point, Long> {

        Optional<Point> findPointByUsername(String username);

        @Modifying
        @Transactional
        @Query("update Point P set P.totalPoint = P.totalPoint + :point  where P.username = :username ")
        void pointCharge(int point , String username);
//
//        @Modifying
//        @Transactional
//        @Query("update Point P set P.point = P.point - :point  where P.username = :username ")
//        void pointWithdraw(int point , String username);

        @Transactional
        void deleteByUsername(String username);

}
