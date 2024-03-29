package com.bit.envdev.contentsRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bit.envdev.contentsEntity.Contents;

@Repository
public interface ContentsRepositoty extends JpaRepository<Contents, Integer>{
    
}
