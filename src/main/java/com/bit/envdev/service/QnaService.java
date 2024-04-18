package com.bit.envdev.service;

import com.bit.envdev.dto.QnaDTO;
import com.bit.envdev.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QnaService {
    void sendQna(QnaDTO qnaDTO);

    List<QnaDTO> get4Qna();

    long getQnaUserCount();

    Page<QnaDTO> searchData(Pageable pageable, String searchKeyword, String searchCondition);

    void answered(QnaDTO qnaDTO);

    Page<QnaDTO> getMyQnaData(Pageable pageable, Member member);

    long countByMemberId(Member entity);
}
