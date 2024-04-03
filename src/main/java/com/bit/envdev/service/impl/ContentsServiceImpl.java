package com.bit.envdev.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.bit.envdev.dto.ResponseDTO;
import com.bit.envdev.dto.SectionDTO;
import com.bit.envdev.dto.SectionSubDTO;
import com.bit.envdev.entity.Contents;
import com.bit.envdev.entity.Section;
import com.bit.envdev.entity.SectionSub;
import com.bit.envdev.repository.SectionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bit.envdev.dto.ContentsDTO;

import com.bit.envdev.entity.Member;
import com.bit.envdev.repository.ContentsRepositoty;
import com.bit.envdev.repository.MemberRepository;
import com.bit.envdev.service.ContentsService;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class ContentsServiceImpl implements ContentsService {

    private final ContentsRepositoty contentsRepository;
    private final SectionRepository sectionRepository;
    private final MemberRepository memberRepository;

    //// 기본정보 저장
    public Contents createContents(ContentsDTO contentsDTO, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Contents contents = contentsDTO.toEntity(member);
        return contentsRepository.save(contents);
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

    @Override
    public ContentsDTO findById(int contentsId) {
        return contentsRepository.findById(contentsId).orElseThrow().toDTO();
    }

//    @Override
//    public ResponseDTO<ContentsDTO> getContentsDetail(int contentsId, Long id) {
//        Contents contents = contentsRepository.findById(contentsId)
//                .orElseThrow(() -> new EntityNotFoundException("Contents not found for this id :: " + contentsId));
//
//        // Contents 엔티티를 ContentsDTO로 변환
//        ContentsDTO contentsDTO = contents.toDTO();
//
//
//
//        return new ResponseDTO<>(contentsDTO);
//    }

}
