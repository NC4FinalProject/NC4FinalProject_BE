package com.bit.envdev.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import com.bit.envdev.entity.Contents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;



@Repository
public interface ContentsRepository extends JpaRepository<Contents, Integer>{
    List<Contents> findTop4ByOrderByRegDateDesc();
}
