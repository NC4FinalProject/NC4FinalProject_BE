package com.bit.envdev.service;

import com.bit.envdev.dto.MemberDTO;
import com.bit.envdev.dto.MemberGraphDTO;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MemberService {
    MemberDTO join(MemberDTO memberDTO);

    MemberDTO login(MemberDTO memberDTO);

    @Transactional
    void resign(String memberDTO);

    MemberDTO emailCheck(MemberDTO memberDTO);

    MemberDTO nicknameCheck(MemberDTO memberDTO);

    MemberDTO updateProfile(String fileString, MemberDTO memberDTO);

    MemberDTO findByUsername(String username);

    MemberDTO updateUserNickname(String userNickname, MemberDTO memberDTO);

    MemberDTO wannabeTeacher(MemberDTO memberDTO);

    String getProfileImageUrl(String noticeWriter);

    List<MemberDTO> find4User();

    List<MemberGraphDTO> getRegistrationCount();

    List<MemberGraphDTO> getTotalUserCount();

    List<MemberGraphDTO> getMonthTotalUserCount();

    List<MemberGraphDTO> getMonthlyUserCount();

    Page<MemberDTO> searchAll(Pageable pageable, String searchKeyword, String searchCondition);

    MemberDTO emailVerification(MemberDTO memberDTO);
}
