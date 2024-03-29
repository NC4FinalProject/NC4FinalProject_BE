package com.bit.envdev.contentsService;

import java.util.List;

import com.bit.envdev.contentsDTO.ContentsDTO;
import com.bit.envdev.dto.BoardDTO;

public interface ContentsService {
        
    void insert(ContentsDTO contentsDTO);

    List<ContentsDTO> findByContents(int contentsId);

    List<ContentsDTO> findByAllContents();

    void update(ContentsDTO contentsDTO);

    void delete(long id);
    
}
