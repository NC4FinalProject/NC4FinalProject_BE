package com.bit.envdev.service.impl;


import com.bit.envdev.dto.MemberDTO;
import com.bit.envdev.dto.ResponseDTO;
import com.bit.envdev.entity.Member;
import com.bit.envdev.jwt.JwtTokenProvider;
import com.bit.envdev.repository.MemberRepository;
import com.bit.envdev.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public MemberDTO join(MemberDTO memberDTO) {
        //유효성 검사
        if(memberDTO == null || memberDTO.getUsername() == null || memberDTO.getPassword() == null) {
            throw new RuntimeException("Invalid Argument");
        }

        //username 중복 체크
        if(memberRepository.existsByUsername(memberDTO.getUsername())) {
            throw new RuntimeException("already exist username");
        }

        Member joinMember = memberRepository.save(memberDTO.toEntity());

        return joinMember.toDTO();
    }

    @Override
    public MemberDTO login(MemberDTO memberDTO) {
        // username으로 조회
        Optional<Member> loginMember = memberRepository.findByUsername(memberDTO.getUsername());

        if(loginMember.isEmpty()) {
            throw new RuntimeException("not exist username");
        }

        // 암호화되어 있는 비밀번호와 사용자가 입력한 비밀번호 비교
        if(!passwordEncoder.matches(memberDTO.getPassword(), loginMember.get().getPassword())) {
            throw new RuntimeException("wrong password");
        }

        MemberDTO loginMemberDTO = loginMember.get().toDTO();

        // JWT 토큰 생성후 DTO에 세팅
        loginMemberDTO.setToken(jwtTokenProvider.create(loginMember.get()));

        return loginMemberDTO;
    }

    @Override
    public MemberDTO emailCheck(MemberDTO memberDTO) {
        
        if(!memberRepository.findByUsername(memberDTO.getUsername()).isEmpty()) {
            throw new RuntimeException("already exist username");
        }

        return memberDTO;
    }

    @Override
    public MemberDTO nicknameCheck(MemberDTO memberDTO) {

        if(!memberRepository.findByUserNickname(memberDTO.getUserNickname()).isEmpty()) {
            throw new RuntimeException("already exist userNickname");
        }

        return memberDTO;
    }

    @Override
    public MemberDTO updateProfile(String fileString, MemberDTO memberDTO) {

        MemberDTO NewMemberDTO = memberRepository.findByUsername(memberDTO.getUsername()).get().toDTO();
        NewMemberDTO.setProfileFile(fileString);

        Member joinMember = memberRepository.save(NewMemberDTO.toEntity());
        return joinMember.toDTO();
    }

    @Override
    public MemberDTO findByUsername(String username) {
       
        MemberDTO NewMemberDTO = memberRepository.findByUsername(username).get().toDTO();
        return NewMemberDTO;
    }
}
