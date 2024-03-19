package com.bit.envdev.service;

import com.bit.envdev.dto.MemberDTO;

public interface MemberService {
    MemberDTO join(MemberDTO memberDTO);

    MemberDTO login(MemberDTO memberDTO);

    MemberDTO emailCheck(MemberDTO memberDTO);

    MemberDTO nicknameCheck(MemberDTO memberDTO);
    
}
