package com.bit.envdev.service.impl;


import com.bit.envdev.common.FileUtils;
import com.bit.envdev.dto.*;
import com.bit.envdev.entity.*;
import com.bit.envdev.repository.ContentsRepository;
import com.bit.envdev.repository.MemberRepository;
import com.bit.envdev.repository.SectionRepository;
import com.bit.envdev.repository.VideoRepository;
import com.bit.envdev.service.ContentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

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


    public VideoReply saveVideoReply(VideoReplyDTO videoReplyDTO) {
        // Member 엔티티 찾기
        Member member = memberRepository.findById(videoReplyDTO.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("해당 멤버가 존재하지 않습니다. id=" + videoReplyDTO.getMemberId()));

        // VideoId 객체 생성
        VideoId videoId = new VideoId(videoReplyDTO.getContentsId(), videoReplyDTO.getVideoId());
        // Video 엔티티 찾기
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new IllegalArgumentException("해당 비디오가 존재하지 않습니다. id=" + videoId));

        // VideoReply 엔티티 생성 및 Video 엔티티에 추가
        VideoReply videoReply = videoReplyDTO.toEntity(video, member);
        video.getVideoReplyList().add(videoReply); // Video 엔티티의 댓글 리스트에 추가
        videoReply.setVideo(video); // 양방향 매핑 설정

        // 변경된 Video 엔티티 저장
        videoRepository.save(video);

        System.out.println("올 비디오 댓글 저장 ㅊㅋ");
//        videoReply
        return null; // 저장된 VideoReply 반환
    }


    @Override
    public ContentsDTO findById(int contentsId) {
        // Contents 엔티티 조회
        Contents contents = contentsRepository.findById(contentsId)
                .orElseThrow(() -> new NoSuchElementException("Contents not found"));
        // Contents 엔티티를 DTO로 변환
        ContentsDTO contentsDTO = contents.toDTO();
        // Member의 userNickname을 ContentsDTO에 설정
        contentsDTO.setUserNickname(contents.getMember().getUserNickname());
        return contentsDTO;
    }

    @Override
    public List<ContentsDTO> findAll() {
        // Contents 엔티티의 리스트를 조회
        List<Contents> contentsList = contentsRepository.findAll();

        // 조회된 Contents 엔티티 리스트를 ContentsDTO 리스트로 변환
        List<ContentsDTO> contentsDTOList = contentsList.stream()
                .map(Contents::toDTO) // Contents 엔티티를 ContentsDTO로 변환
                .collect(Collectors.toList());

        return contentsDTOList;
    }


    @Override
    public List<ContentsDTO> get4Contents() {
        return  contentsRepository.findTop4ByOrderByRegDateDesc().stream()
                .map(Contents::toDTO)
                .collect(Collectors.toList());
    }
    @Override
    public List<VideoReplyDTO> getVideoReplyList(int contentsId, int videoId) {
        // 복합 키 인스턴스 생성
        VideoId videoIdObj = new VideoId(contentsId, videoId);


        // Video 엔티티 조회
        Optional<Video> videoOpt = videoRepository.findById(videoIdObj);

        if (!videoOpt.isPresent()) {
            return new ArrayList<>();
        }

        Video video = videoOpt.get();

        // VideoReply 목록을 VideoReplyDTO 목록으로 변환
        // return video.getVideoReplyList().stream()
        //        .map(VideoReply::toDTO) // VideoReply 엔티티를 VideoReplyDTO로 변환
        //        .collect(Collectors.toList());

        return video.getVideoReplyList().stream()
                .map(videoReply -> {
                    VideoReplyDTO dto = videoReply.toDTO(); // 기존 변환 로직
                    // 여기서 Member 엔티티에서 필요한 정보를 가져와 DTO에 설정
                    Member member = videoReply.getMember(); // VideoReply에서 Member 정보를 가져옴
                    dto.setUsername(member.getUsername());
                    dto.setUserNickname(member.getUserNickname());
                    dto.setProfileFile(member.getProfileFile());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Page<ContentsDTO> searchData(Pageable pageable, String searchKeyword, String searchCondition) {
        Page<Contents> contentsList = contentsRepository.findAll(pageable);
        if ("all".equals(searchCondition)) {
            contentsList = contentsRepository.findByContentsTitleContaining(pageable, searchKeyword);
        } else {
            System.out.println("searchCondition : " + searchCondition);
            contentsList = contentsRepository.findByCategoryAndContentsTitleContaining(pageable, searchCondition, searchKeyword);
        }
        return contentsList.map(Contents::toDTO);
    }
}
