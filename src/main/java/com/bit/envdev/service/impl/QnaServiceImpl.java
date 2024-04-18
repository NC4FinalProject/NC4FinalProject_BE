package com.bit.envdev.service.impl;

import com.bit.envdev.dto.QnaDTO;
import com.bit.envdev.entity.Member;
import com.bit.envdev.entity.Qna;
import com.bit.envdev.repository.QnaRepository;
import com.bit.envdev.service.QnaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QnaServiceImpl implements QnaService {
    final QnaRepository qnaRepository;

    @Override
    public void sendQna(QnaDTO qnaDTO) {
        qnaDTO.setCreatedAt(qnaDTO.getCreatedAt());
        qnaDTO.setContent(qnaDTO.getContent());
        qnaDTO.setCategory(qnaDTO.getCategory());
        qnaDTO.setAskUser(qnaDTO.getAskUser());
        qnaRepository.save(qnaDTO.toEntity());
    }

    @Override
    public List<QnaDTO> get4Qna() {
        List<Qna> recentQnaList = qnaRepository.get4Qna();
        return recentQnaList.stream()
                .map(Qna::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public long getQnaUserCount() {
        return qnaRepository.countByAnsweredFalse();
    }

    @Override
    public Page<QnaDTO> searchData(Pageable pageable, String searchKeyword, String searchCondition) {
        Page<Qna> qnaList = qnaRepository.findAll(pageable);
        if ("all".equals(searchCondition)) {
            qnaList = qnaRepository.searchData(pageable, searchKeyword);
        } else {
            System.out.println("searchCondition : " + searchCondition);
            qnaList = qnaRepository.findByMemberAndContentContaining(pageable, searchCondition, searchKeyword);
        }
        return qnaList.map(Qna::toDTO);
    }

    @Override
    public void answered(QnaDTO qnaDTO) {
        Qna qna = qnaRepository.findById(qnaDTO.getId()).orElseThrow();
        qna.setCreatedAt(qnaDTO.getCreatedAt());
        qna.setContent(qnaDTO.getContent());
        qna.setAnswerUser(qnaDTO.getAnswerUser());
        qna.setAnswered(true);
        qnaRepository.save(qna);
    }

    @Override
    public Page<QnaDTO> getMyQnaData(Pageable pageable, Member member) {
        Page<Qna> qnaPage = qnaRepository.findByAskUserOrderByIdDesc(pageable, member);
        List<QnaDTO> qnaDTOList = qnaPage.getContent().stream()
                .map(Qna::toDTO)
                .collect(Collectors.toList());
        return new PageImpl<>(qnaDTOList, pageable, qnaPage.getTotalElements());
    }

    @Override
    public long countByMemberId(Member entity) {
        return qnaRepository.countByAskUser(entity);
    }
}

