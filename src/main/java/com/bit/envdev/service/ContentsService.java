package com.bit.envdev.service;

import java.util.List;

import com.bit.envdev.dto.*;
import com.bit.envdev.entity.Contents;
import com.bit.envdev.entity.Section;
import com.bit.envdev.entity.Video;
import com.bit.envdev.entity.VideoReply;
import org.springframework.web.multipart.MultipartFile;


public interface ContentsService {
    Contents createContents(ContentsDTO contentsDTO, Long id, MultipartFile thumbnail );
    Video createVideo(VideoDTO videoDTO, Contents createdContents, Long id,  MultipartFile videoFile);
    Section createSection(SectionDTO sectionDTO, Contents createdContents);
    VideoReply saveVideoReply(VideoReplyDTO videoReplyDTO);
    ContentsDTO findById(int contentsId);
    List<ContentsDTO> findAll();


 }
