package com.bit.envdev.contentsService;

import java.util.List;

import com.bit.envdev.contentsDTO.ContentsDTO;
import com.bit.envdev.contentsEntity.Contents;

public interface ContentsService {
        
    Contents createContents(ContentsDTO contentsDTO, Long id);

    List<ContentsDTO> findByContents(int contentsId);

    List<ContentsDTO> findByAllContents();

    void update(ContentsDTO contentsDTO);

    void delete(long id);
    
}
