package com.bit.envdev.service;
import com.bit.envdev.dto.BoardDTO;

import java.util.List;


public interface BoardService {
    
    void save(BoardDTO boardDTO);

    List<BoardDTO> findByUsername(String username);

    void modify(BoardDTO boardDTO);

    void remove(long id);
    
}
