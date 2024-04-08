package com.bit.envdev.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.bit.envdev.dto.*;
import com.bit.envdev.entity.*;
import com.bit.envdev.repository.ContentsRepository;
import com.bit.envdev.repository.SectionRepository;
import com.bit.envdev.repository.VideoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bit.envdev.repository.MemberRepository;
import com.bit.envdev.service.ContentsService;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class ContentsServiceImpl implements ContentsService {

    private final MemberRepository memberRepository;

    private final ContentsRepository contentsRepository;
    private final SectionRepository sectionRepository;
    private final VideoRepository videoRepository;



    //// 기본정보 저장
    public Contents createContents(ContentsDTO contentsDTO, Long memberId, String fileString) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        contentsDTO.setThumbnail(fileString);
        Contents contents = contentsDTO.toEntity(member);

        return contentsRepository.save(contents);
    }

    @Transactional
    public Video createVideo(VideoDTO videoDTO, Contents createdContents, Long memberId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

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
        List<SectionSubDTO> sectionSubDTOList = sectionDTO.getSectionSubList();
        // 메인 섹션 디티오를 엔티티로 변환
        Section section = sectionDTO.toEntity(createdContents);
        // 서브 섹션 디티오를 엔티티로 변환
        List<SectionSub> sectionSubList = sectionSubDTOList.stream()
                .map(sectionSubDTO -> sectionSubDTO.toEntity(section))
                .toList();
        // DTO로 변환된 SectionSub 객체들의 리스트(sectionSubList)를 순회하면서, 각각의 SectionSub 객체를 Section 객체의 서브 섹션 목록에 추가
        sectionSubList.forEach(section::addSectionSubList);
        sectionRepository.save(section);
        return section;
    }

//    @Override
//    public ContentsDTO uploadThumbnail(String fileString, ContentsDTO contentsDTO, MemberDTO memberDTO) {
//        System.out.println("아니 서비스 자체가 오긴 오나고?????????");
//        // MemberDTO로부터 Member 엔티티 조회
//        Member member = memberRepository.findByUsername(memberDTO.getUsername())
//                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + memberDTO.getUsername()));
//        contentsDTO.setThumbnail(fileString);
//        System.out.println("==========>>> 서비스단에서의 dto에 넣는 중"+fileString);
//        System.out.println("==========>>> 서비스단에서의 반영된 dto의 내용"+contentsDTO);
//        // Member 엔티티를 포함하여 Contents 엔티티 저장
//        Contents addThumbnailContents = contentsRepository.save(contentsDTO.toEntity(member));
//        return addThumbnailContents.toDTO();
//    }

    @Override
    public ContentsDTO findById(int contentsId) {
        return contentsRepository.findById(contentsId).orElseThrow().toDTO();
    }

//    @Override
//    public SectionDTO findById(int sectionId) {
//        return sectionRepository.findById(sectionId).orElseThrow().toDTO();
//    }

//    @Override
//    public VideoDTO findById(int videoId) {
//        return videoRepository.findById(videoId).orElseThrow().toDTO();
//    }
}
