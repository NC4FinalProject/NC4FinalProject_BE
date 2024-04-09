package com.bit.envdev.repository;


import com.bit.envdev.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bit.envdev.entity.Contents;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ContentsRepository extends JpaRepository<Contents, Integer>{
}
