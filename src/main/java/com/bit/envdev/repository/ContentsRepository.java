package com.bit.envdev.repository;


import com.bit.envdev.entity.Contents;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ContentsRepository extends JpaRepository<Contents, Integer>{
    List<Contents> findTop4ByOrderByRegDateDesc();

    Page<Contents> findByContentsTitleContaining(Pageable pageable, String searchKeyword);

    Page<Contents> findByCategoryAndContentsTitleContaining(Pageable pageable, String searchCondition, String searchKeyword);

    List<Contents> findTop12ByOrderByRegDateDesc();

    @Query(value = "SELECT * FROM contents ORDER BY RAND() LIMIT 12", nativeQuery = true)
    List<Contents> findTop12ByOrderByIdAsc();
}
