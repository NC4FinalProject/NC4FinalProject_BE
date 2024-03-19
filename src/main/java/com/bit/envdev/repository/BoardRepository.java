package com.bit.envdev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bit.envdev.entity.Board;
import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long>  {
    
    List<Board> findByMemberUsername(String username);
}
