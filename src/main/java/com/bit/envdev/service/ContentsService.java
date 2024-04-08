package com.bit.envdev.service;

import java.util.List;

import com.bit.envdev.dto.*;
import com.bit.envdev.entity.Contents;
import com.bit.envdev.entity.Section;
import com.bit.envdev.entity.Video;


public interface ContentsService {
    Contents createContents(ContentsDTO contentsDTO, Long id, String fileString);
    Video createVideo(VideoDTO videoDTO, Contents createdContents, Long id);
    Section createSection(SectionDTO sectionDTO, Contents createdContents);

//    ContentsDTO uploadThumbnail(String fileString, ContentsDTO contentsDTO, MemberDTO memberDTO);


    ContentsDTO findById(int contentsId);
//    SectionDTO findById(int sectionId);
//    VideoDTO findById(int videoId);

//     ResponseDTO<ContentsDTO> getContentsDetail(int contentsId, Long id);

 }
