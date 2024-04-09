package com.bit.envdev.service.impl;

import java.util.List;

import com.bit.envdev.common.FileUtils;
import com.bit.envdev.dto.*;
import com.bit.envdev.entity.*;
import com.bit.envdev.repository.ContentsRepository;
import com.bit.envdev.repository.SectionRepository;
import com.bit.envdev.repository.VideoRepository;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bit.envdev.repository.MemberRepository;
import com.bit.envdev.service.ContentsService;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ContentsServiceImpl implements ContentsService {

    private final MemberRepository memberRepository;

    private final ContentsRepository contentsRepository;
    private final SectionRepository sectionRepository;
    private final VideoRepository videoRepository;

    private final FileUtils fileUtils;

    public String filePath(MultipartFile file){
        String filePath = null;
        if (file.getOriginalFilename() != null && !file.getOriginalFilename().isEmpty()) {
            FileDTO fileDTO = fileUtils.uploadFile(file, "board/");  // 오브젝트 스토리지에 업로드하면서 만들어진 디티오를 파일 디티오에 담음
            filePath = (fileDTO.getItemFilePath()+fileDTO.getItemFileName()); // 파일 디티오에 경로와 파일 디티오에 파일 네임을 담은 fileString에 담음
        }
        return filePath;
    }

    //// 기본정보 저장
    public Contents createContents(ContentsDTO contentsDTO, Long memberId, MultipartFile thumbnail ) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String filePath = filePath(thumbnail);
        contentsDTO.setThumbnail(filePath);
        Contents contents = contentsDTO.toEntity(member);

        return contentsRepository.save(contents);
    }
    @Transactional
    public Video createVideo(VideoDTO videoDTO, Contents createdContents, Long memberId, MultipartFile videoFile) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        // 비디오 파일 멀티파일을 오브젝트 스토리지에 삽입
        videoDTO.setVideoPath(filePath(videoFile));
        // 비디오 디티오를 엔티티로 변환
        Video video = videoDTO.toEntity(createdContents);
        // 비디오 댓글 디티오를 엔티티로 변환
        List<VideoReplyDTO> videoReplyDTOList = videoDTO.getVideoReplyList();
        List<VideoReply> videoReplyList = videoReplyDTOList.stream()
                .map(videoReplyDTO -> videoReplyDTO.toEntity(video, member))
                .toList();
        // DTO로 변환된 VideoReply 객체들의 리스트(videoReplyList)를 순회하면서, 각각의 VideoReply 객체를 Video 객체의 하위 목록에 추가
        videoReplyList.forEach(video::addVideoReplyList);
        videoRepository.save(video);
        return video;
    }
    //// 카테고리 섹션
    @Transactional
    public Section createSection(SectionDTO sectionDTO, Contents createdContents) {
        // 메인 섹션 디티오를 엔티티로 변환
        Section section = sectionDTO.toEntity(createdContents);
        // 서브 섹션 디티오를 엔티티로 변환
        List<SectionSubDTO> sectionSubDTOList = sectionDTO.getSectionSubList();
        List<SectionSub> sectionSubList = sectionSubDTOList.stream()
                .map(sectionSubDTO -> sectionSubDTO.toEntity(section))
                .toList();
        // DTO로 변환된 SectionSub 객체들의 리스트(sectionSubList)를 순회하면서, 각각의 SectionSub 객체를 Section 객체의 서브 섹션 목록에 추가
        sectionSubList.forEach(section::addSectionSubList);
        sectionRepository.save(section);
        return section;
    }


    @Override
    public ContentsDTO findById(int contentsId) {
        return contentsRepository.findById(contentsId).orElseThrow().toDTO();
    }

}

//        if(videoFile != null){
//        for(int i = 0; i < videoDTOList.size(); i++) {
//        videoDTOList.get(i).setVideoPath(filePath(videoFile[i]));
//        }
//        }
//        List<Video> videoList = videoDTOList.stream().map(videoDTO -> videoDTO.toEntity(contents)).toList();
//        videoList.forEach(contents::addVideoFile);
