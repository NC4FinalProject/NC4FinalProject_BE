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

import java.util.*;
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
            FileDTO fileDTO = fileUtils.uploadFile(file, "video/");  // 오브젝트 스토리지에 업로드하면서 만들어진 디티오를 파일 디티오에 담음
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

        List<ContentsFileDTO> contentsFileDTOList = contentsDTO.getContentsFileDTOList();

        if(contentsFileDTOList != null) {
            for(ContentsFileDTO contentsFileDTO : contentsFileDTOList) {
                ContentsFile contentsFile = contentsFileDTO.toEntity(contents);
                contents.getContentsFileList().add(contentsFile);
            }
        }

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
    public ContentsDTO findById(int contentsId, CustomUserDetails customUserDetails) {
        Contents contents = new Contents();
        if(customUserDetails == null) {
            // Contents 엔티티 조회
            contents = contentsRepository.searchById(contentsId);
        } else {
            contents = contentsRepository.searchByIdMemberId(contentsId, customUserDetails.getMember().getMemberId());
        }
        // Contents 엔티티를 DTO로 변환
        ContentsDTO contentsDTO = contents.toDTO();
        // Member의 userNickname을 ContentsDTO에 설정
        contentsDTO.setUserNickname(contents.getMember().getUserNickname());
        contentsDTO.setProfileFile(contents.getMember().getProfileFile());

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

    @Override
    public List<ContentsDTO> get12RandomContents() {
        return contentsRepository.findTop12ByOrderByIdAsc().stream()
                .map(Contents::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<ContentsDTO> searchAll(Pageable pageable, String category, String pricePattern, String orderType, String searchKeyword) {
        if(searchKeyword.isEmpty()) {
            if (category.isEmpty() && pricePattern.isEmpty() && orderType.isEmpty()) {
                return contentsRepository.searchAll(pageable).map(Contents::toDTO);
            } else {
                if (orderType.isEmpty()) {
                    if (category.isEmpty() && !pricePattern.isEmpty()) {
                        if (pricePattern.equalsIgnoreCase("무료")) {
                            return contentsRepository.searchAllFree(pageable).map(Contents::toDTO);
                        } else if (pricePattern.equalsIgnoreCase("유료")) {
                            return contentsRepository.searchAllPay(pageable).map(Contents::toDTO);
                        } else if (pricePattern.equalsIgnoreCase("국가")) {
                            return contentsRepository.searchAllNational(pageable).map(Contents::toDTO);
                        }
                    } else if (!category.isEmpty() && pricePattern.isEmpty()) {
                        return contentsRepository.searchAllCategory(pageable, category).map(Contents::toDTO);
                    } else if (!category.isEmpty() && !pricePattern.isEmpty()) {
                        if (pricePattern.equalsIgnoreCase("무료")) {
                            return contentsRepository.searchAllCategoryFree(pageable, category).map(Contents::toDTO);
                        } else if (pricePattern.equalsIgnoreCase("유료")) {
                            return contentsRepository.searchAllCategoryPay(pageable, category).map(Contents::toDTO);
                        } else if (pricePattern.equalsIgnoreCase("국가")) {
                            return contentsRepository.searchAllCategoryNational(pageable, category).map(Contents::toDTO);
                        }
                    } else {

                    }
                } else {
                    if (orderType.equalsIgnoreCase("판매순")) {
                        if (category.isEmpty() && !pricePattern.isEmpty()) {
                            if (pricePattern.equalsIgnoreCase("무료")) {
                                return contentsRepository.searchAllFreeSale(pageable).map(Contents::toDTO);
                            } else if (pricePattern.equalsIgnoreCase("유료")) {
                                return contentsRepository.searchAllPaySale(pageable).map(Contents::toDTO);
                            } else if (pricePattern.equalsIgnoreCase("국가")) {
                                return contentsRepository.searchAllNationalSale(pageable).map(Contents::toDTO);
                            }
                        } else if (!category.isEmpty() && pricePattern.isEmpty()) {
                            return contentsRepository.searchAllCategorySale(pageable, category).map(Contents::toDTO);
                        } else if (!category.isEmpty() && !pricePattern.isEmpty()) {
                            if (pricePattern.equalsIgnoreCase("무료")) {
                                return contentsRepository.searchAllCategoryFreeSale(pageable, category).map(Contents::toDTO);
                            } else if (pricePattern.equalsIgnoreCase("유료")) {
                                return contentsRepository.searchAllCategoryPaySale(pageable, category).map(Contents::toDTO);
                            } else if (pricePattern.equalsIgnoreCase("국가")) {
                                return contentsRepository.searchAllCategoryNationalSale(pageable, category).map(Contents::toDTO);
                            }
                        } else {
                            return contentsRepository.searchAllSale(pageable).map(Contents::toDTO);
                        }
                    } else if (orderType.equalsIgnoreCase("인기순")) {
                        if (category.isEmpty() && !pricePattern.isEmpty()) {
                            if (pricePattern.equalsIgnoreCase("무료")) {
                                return contentsRepository.searchAllFreePop(pageable).map(Contents::toDTO);
                            } else if (pricePattern.equalsIgnoreCase("유료")) {
                                return contentsRepository.searchAllPayPop(pageable).map(Contents::toDTO);
                            } else if (pricePattern.equalsIgnoreCase("국가")) {
                                return contentsRepository.searchAllNationalPop(pageable).map(Contents::toDTO);
                            }
                        } else if (!category.isEmpty() && pricePattern.isEmpty()) {
                            return contentsRepository.searchAllCategoryPop(pageable, category).map(Contents::toDTO);
                        } else if (!category.isEmpty() && !pricePattern.isEmpty()) {
                            if (pricePattern.equalsIgnoreCase("무료")) {
                                return contentsRepository.searchAllCategoryFreePop(pageable, category).map(Contents::toDTO);
                            } else if (pricePattern.equalsIgnoreCase("유료")) {
                                return contentsRepository.searchAllCategoryPayPop(pageable, category).map(Contents::toDTO);
                            } else if (pricePattern.equalsIgnoreCase("국가")) {
                                return contentsRepository.searchAllCategoryNationalPop(pageable, category).map(Contents::toDTO);
                            }
                        } else {
                            return contentsRepository.searchAllPop(pageable).map(Contents::toDTO);
                        }
                    } else {
                        if (category.isEmpty() && !pricePattern.isEmpty()) {
                            if (pricePattern.equalsIgnoreCase("무료")) {
                                return contentsRepository.searchAllFreeReg(pageable).map(Contents::toDTO);
                            } else if (pricePattern.equalsIgnoreCase("유료")) {
                                return contentsRepository.searchAllPayReg(pageable).map(Contents::toDTO);
                            } else if (pricePattern.equalsIgnoreCase("국가")) {
                                return contentsRepository.searchAllNationalReg(pageable).map(Contents::toDTO);
                            }
                        } else if (!category.isEmpty() && pricePattern.isEmpty()) {
                            return contentsRepository.searchAllCategoryReg(pageable, category).map(Contents::toDTO);
                        } else if (!category.isEmpty() && !pricePattern.isEmpty()) {
                            if (pricePattern.equalsIgnoreCase("무료")) {
                                return contentsRepository.searchAllCategoryFreeReg(pageable, category).map(Contents::toDTO);
                            } else if (pricePattern.equalsIgnoreCase("유료")) {
                                return contentsRepository.searchAllCategoryPayReg(pageable, category).map(Contents::toDTO);
                            } else if (pricePattern.equalsIgnoreCase("국가")) {
                                return contentsRepository.searchAllCategoryNationalReg(pageable, category).map(Contents::toDTO);
                            }
                        } else {
                            return contentsRepository.searchAllReg(pageable).map(Contents::toDTO);
                        }
                    }
                }
            }
        } else {
            if (category.isEmpty() && pricePattern.isEmpty() && orderType.isEmpty()) {
                return contentsRepository.searchAllkeyword(pageable, searchKeyword).map(Contents::toDTO);
            } else {
                if (orderType.isEmpty()) {
                    if (category.isEmpty() && !pricePattern.isEmpty()) {
                        if (pricePattern.equalsIgnoreCase("무료")) {
                            return contentsRepository.searchAllFreekeyword(pageable, searchKeyword).map(Contents::toDTO);
                        } else if (pricePattern.equalsIgnoreCase("유료")) {
                            return contentsRepository.searchAllPaykeyword(pageable, searchKeyword).map(Contents::toDTO);
                        } else if (pricePattern.equalsIgnoreCase("국가")) {
                            return contentsRepository.searchAllNationalkeyword(pageable, searchKeyword).map(Contents::toDTO);
                        }
                    } else if (!category.isEmpty() && pricePattern.isEmpty()) {
                        return contentsRepository.searchAllCategorykeyword(pageable, category, searchKeyword).map(Contents::toDTO);
                    } else if (!category.isEmpty() && !pricePattern.isEmpty()) {
                        if (pricePattern.equalsIgnoreCase("무료")) {
                            return contentsRepository.searchAllCategoryFreekeyword(pageable, category, searchKeyword).map(Contents::toDTO);
                        } else if (pricePattern.equalsIgnoreCase("유료")) {
                            return contentsRepository.searchAllCategoryPaykeyword(pageable, category, searchKeyword).map(Contents::toDTO);
                        } else if (pricePattern.equalsIgnoreCase("국가")) {
                            return contentsRepository.searchAllCategoryNationalkeyword(pageable, category, searchKeyword).map(Contents::toDTO);
                        }
                    } else {

                    }
                } else {
                    if (orderType.equalsIgnoreCase("판매순")) {
                        if (category.isEmpty() && !pricePattern.isEmpty()) {
                            if (pricePattern.equalsIgnoreCase("무료")) {
                                return contentsRepository.searchAllFreeSalekeyword(pageable, searchKeyword).map(Contents::toDTO);
                            } else if (pricePattern.equalsIgnoreCase("유료")) {
                                return contentsRepository.searchAllPaySalekeyword(pageable, searchKeyword).map(Contents::toDTO);
                            } else if (pricePattern.equalsIgnoreCase("국가")) {
                                return contentsRepository.searchAllNationalSalekeyword(pageable, searchKeyword).map(Contents::toDTO);
                            }
                        } else if (!category.isEmpty() && pricePattern.isEmpty()) {
                            return contentsRepository.searchAllCategorySalekeyword(pageable, category, searchKeyword).map(Contents::toDTO);
                        } else if (!category.isEmpty() && !pricePattern.isEmpty()) {
                            if (pricePattern.equalsIgnoreCase("무료")) {
                                return contentsRepository.searchAllCategoryFreeSalekeyword(pageable, category, searchKeyword).map(Contents::toDTO);
                            } else if (pricePattern.equalsIgnoreCase("유료")) {
                                return contentsRepository.searchAllCategoryPaySalekeyword(pageable, category, searchKeyword).map(Contents::toDTO);
                            } else if (pricePattern.equalsIgnoreCase("국가")) {
                                return contentsRepository.searchAllCategoryNationalSalekeyword(pageable, category, searchKeyword).map(Contents::toDTO);
                            }
                        } else {
                            return contentsRepository.searchAllSale(pageable).map(Contents::toDTO);
                        }
                    } else if (orderType.equalsIgnoreCase("인기순")) {
                        if (category.isEmpty() && !pricePattern.isEmpty()) {
                            if (pricePattern.equalsIgnoreCase("무료")) {
                                return contentsRepository.searchAllFreePopkeyword(pageable, searchKeyword).map(Contents::toDTO);
                            } else if (pricePattern.equalsIgnoreCase("유료")) {
                                return contentsRepository.searchAllPayPopkeyword(pageable, searchKeyword).map(Contents::toDTO);
                            } else if (pricePattern.equalsIgnoreCase("국가")) {
                                return contentsRepository.searchAllNationalPopkeyword(pageable, searchKeyword).map(Contents::toDTO);
                            }
                        } else if (!category.isEmpty() && pricePattern.isEmpty()) {
                            return contentsRepository.searchAllCategoryPopkeyword(pageable, category, searchKeyword).map(Contents::toDTO);
                        } else if (!category.isEmpty() && !pricePattern.isEmpty()) {
                            if (pricePattern.equalsIgnoreCase("무료")) {
                                return contentsRepository.searchAllCategoryFreePopkeyword(pageable, category, searchKeyword).map(Contents::toDTO);
                            } else if (pricePattern.equalsIgnoreCase("유료")) {
                                return contentsRepository.searchAllCategoryPayPopkeyword(pageable, category, searchKeyword).map(Contents::toDTO);
                            } else if (pricePattern.equalsIgnoreCase("국가")) {
                                return contentsRepository.searchAllCategoryNationalPopkeyword(pageable, category, searchKeyword).map(Contents::toDTO);
                            }
                        } else {
                            return contentsRepository.searchAllPopkeyword(pageable, searchKeyword).map(Contents::toDTO);
                        }
                    } else {
                        if (category.isEmpty() && !pricePattern.isEmpty()) {
                            if (pricePattern.equalsIgnoreCase("무료")) {
                                return contentsRepository.searchAllFreeRegkeyword(pageable, searchKeyword).map(Contents::toDTO);
                            } else if (pricePattern.equalsIgnoreCase("유료")) {
                                return contentsRepository.searchAllPayRegkeyword(pageable, searchKeyword).map(Contents::toDTO);
                            } else if (pricePattern.equalsIgnoreCase("국가")) {
                                return contentsRepository.searchAllNationalRegkeyword(pageable, searchKeyword).map(Contents::toDTO);
                            }
                        } else if (!category.isEmpty() && pricePattern.isEmpty()) {
                            return contentsRepository.searchAllCategoryRegkeyword(pageable, category, searchKeyword).map(Contents::toDTO);
                        } else if (!category.isEmpty() && !pricePattern.isEmpty()) {
                            if (pricePattern.equalsIgnoreCase("무료")) {
                                return contentsRepository.searchAllCategoryFreeRegkeyword(pageable, category, searchKeyword).map(Contents::toDTO);
                            } else if (pricePattern.equalsIgnoreCase("유료")) {
                                return contentsRepository.searchAllCategoryPayRegkeyword(pageable, category, searchKeyword).map(Contents::toDTO);
                            } else if (pricePattern.equalsIgnoreCase("국가")) {
                                return contentsRepository.searchAllCategoryNationalRegkeyword(pageable, category, searchKeyword).map(Contents::toDTO);
                            }
                        } else {
                            return contentsRepository.searchAllRegkeyword(pageable, searchKeyword).map(Contents::toDTO);
                        }
                    }
                }
            }
        }

        return contentsRepository.searchAll(pageable).map(Contents::toDTO);
    }

    @Override
    public Page<ContentsDTO> searchMyAll(Pageable pageable, Member member) {
        return contentsRepository.searchMyAll(pageable, member.getMemberId()).map(Contents::toDTO);
    }

    @Override
    public Page<ContentsDTO> searchTeacherAll(Pageable pageable, Member member) {
        return contentsRepository.searchTeacherAll(pageable, member.getMemberId()).map(Contents::toDTO);
    }

    @Override
    public void deleteContents(int contentsId) {
        contentsRepository.deleteById(contentsId);
    }

    @Override
    public Page<ContentsDTO> searchBookmarkAll(Pageable pageable, Member member) {
        return contentsRepository.searchBookmarkAll(pageable, member.getMemberId()).map(Contents::toDTO);
    }

    @Override
    public long countByMemberId(Member member) {
        return contentsRepository.countByMember(member);
    }
}