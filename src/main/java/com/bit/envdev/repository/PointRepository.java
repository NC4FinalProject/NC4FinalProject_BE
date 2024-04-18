package com.bit.envdev.repository;


import com.bit.envdev.entity.Point;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointRepository extends JpaRepository<Point, Long>,  PointRepositoryCustom {

}
