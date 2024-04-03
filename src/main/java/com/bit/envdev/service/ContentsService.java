package com.bit.envdev.service;

import java.util.List;

import com.bit.envdev.dto.ContentsDTO;
import com.bit.envdev.dto.ResponseDTO;
import com.bit.envdev.dto.SectionDTO;
import com.bit.envdev.entity.Contents;
import com.bit.envdev.entity.Section;


 public interface ContentsService {
     Contents createContents(ContentsDTO contentsDTO, Long id);

     Section createSection(SectionDTO sectionDTO, Contents createdContents);

     ContentsDTO findById(int contentsId);

//     ResponseDTO<ContentsDTO> getContentsDetail(int contentsId, Long id);

 }
