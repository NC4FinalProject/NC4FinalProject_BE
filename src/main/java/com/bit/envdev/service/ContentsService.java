package com.bit.envdev.service;

import com.bit.envdev.dto.ContentsDTO;
import com.bit.envdev.dto.SectionDTO;
import com.bit.envdev.dto.VideoDTO;
import com.bit.envdev.dto.VideoReplyDTO;
import com.bit.envdev.entity.Contents;
import com.bit.envdev.entity.Section;
import com.bit.envdev.entity.Video;
import com.bit.envdev.entity.VideoReply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface ContentsService {
    Contents createContents(ContentsDTO contentsDTO, Long id, MultipartFile thumbnail );
    Video createVideo(VideoDTO videoDTO, Contents createdContents, Long id,  MultipartFile videoFile);
    Section createSection(SectionDTO sectionDTO, Contents createdContents);
    
    ContentsDTO findById(int contentsId);
    List<ContentsDTO> findAll();


    List<ContentsDTO> get4Contents();


    VideoReply saveVideoReply(VideoReplyDTO videoReplyDTO);
    List<VideoReplyDTO> getVideoReplyList(int contentsId, int videoId);


    Page<ContentsDTO> searchData(Pageable pageable, String searchKeyword, String searchCondition);

    List<ContentsDTO> get12RandomContents();
}

