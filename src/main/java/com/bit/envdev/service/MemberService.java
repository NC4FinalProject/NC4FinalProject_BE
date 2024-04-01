package com.bit.envdev.service;

import com.bit.envdev.dto.MemberDTO;

import java.util.List;

public interface MemberService {
    MemberDTO join(MemberDTO memberDTO);

    MemberDTO login(MemberDTO memberDTO);

    MemberDTO emailCheck(MemberDTO memberDTO);

    MemberDTO nicknameCheck(MemberDTO memberDTO);

    MemberDTO updateProfile(String fileString, MemberDTO memberDTO);

    MemberDTO findByUsername(String username);

    MemberDTO updateUserNickname(String userNickname, MemberDTO memberDTO);

    MemberDTO wannabeTeacher(MemberDTO memberDTO);

    String getProfileImageUrl(String noticeWriter);

    List<MemberDTO> findAll();
}
