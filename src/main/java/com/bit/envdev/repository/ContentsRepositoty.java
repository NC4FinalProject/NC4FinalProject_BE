package com.bit.envdev.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.bit.envdev.entity.Contents;
import org.springframework.stereotype.Repository;


@Repository
public interface ContentsRepositoty extends JpaRepository<Contents, Integer>{
    
}
