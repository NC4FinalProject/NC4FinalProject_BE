package com.bit.envdev.service.impl;


import com.bit.envdev.constant.Role;
import com.bit.envdev.dto.MemberDTO;
import com.bit.envdev.dto.MemberGraphDTO;
import com.bit.envdev.entity.Member;
import com.bit.envdev.entity.MemberGraph;
import com.bit.envdev.jwt.JwtTokenProvider;
import com.bit.envdev.repository.MemberGraphRepository;
import com.bit.envdev.repository.MemberRepository;
import com.bit.envdev.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final PointServiceImpl pointService;
    private final PointHistoryServiceImpl pointHistoryService;
    private final MemberGraphRepository memberGraphRepository;

    @Override
    public MemberDTO join(MemberDTO memberDTO) {
        //유효성 검사
        if(memberDTO.getUsername() == null || memberDTO.getPassword() == null) {
            throw new RuntimeException("Invalid Argument");
        }

        //username 중복 체크
        if(memberRepository.existsByUsername(memberDTO.getUsername())) {
            throw new RuntimeException("already exist username");
        }

        memberDTO.setCreatedAt(LocalDateTime.now().toString());
        memberDTO.setModifiedAt(LocalDateTime.now().toString());
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
    
        if (loginMember.get().getRole().equals(Role.RESIGNED)) {
            throw new RuntimeException("탈퇴한 유저입니다.");
        }
        MemberDTO loginMemberDTO = loginMember.get().toDTO();

        // JWT 토큰 생성후 DTO에 세팅
        loginMemberDTO.setToken(jwtTokenProvider.create(loginMember.get()));

        return loginMemberDTO;
    }

    @Override
    public void resign(String username) {
        // 삭제하는 로직
        // pointService.pointRemove(username);
        // pointHistoryService.pointHistoryRemove(username);
        // memberRepository.deleteByUsername(username);

        // 탈퇴한 회원정보 롤 변경
        Optional<Member> resignMember = memberRepository.findByUsername(username);
        MemberDTO resignMemberDTO = resignMember.get().toDTO();
        resignMemberDTO.setRole(Role.RESIGNED);
        memberRepository.save(resignMemberDTO.toEntity());

        // return resignMemberDTO;
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

    @Override
    public MemberDTO updateUserNickname(String userNickname, MemberDTO memberDTO) {

        MemberDTO NewMemberDTO = memberRepository.findByUsername(memberDTO.getUsername()).get().toDTO();
        NewMemberDTO.setUserNickname(userNickname);

        Member joinMember = memberRepository.save(NewMemberDTO.toEntity());
        return joinMember.toDTO();
    }

    @Override
    public MemberDTO wannabeTeacher(MemberDTO memberDTO) {
        MemberDTO NewMemberDTO = memberRepository.findByUsername(memberDTO.getUsername()).get().toDTO();
        NewMemberDTO.setRole(Role.PRETEACHER);
        Member joinMember = memberRepository.save(NewMemberDTO.toEntity());
        return joinMember.toDTO();
    }

    @Override
    public MemberDTO emailVerification(MemberDTO memberDTO) {
        MemberDTO NewMemberDTO = memberRepository.findByUsername(memberDTO.getUsername()).get().toDTO();
        NewMemberDTO.setEmailVerification(true);
        Member joinMember = memberRepository.save(NewMemberDTO.toEntity());
        return joinMember.toDTO();
    }

    @Override
    public String getProfileImageUrl(String noticeWriter) {
        String profileImageUrl = memberRepository.findByUserNickname(noticeWriter).get().getProfileFile();
        return profileImageUrl;
    }

    @Override
    public List<MemberDTO> find4User() {
        List<Member> members = memberRepository.findTop4ByOrderByIdDesc();
        return members.stream()
                .map(Member::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MemberGraphDTO> getRegistrationCount() {
        List<MemberGraph> memberGraphDTOList = memberGraphRepository.findMemberGraph();
        return memberGraphDTOList.stream()
                .map(MemberGraph::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MemberGraphDTO> getTotalUserCount() {
        List<MemberGraph> memberGraphDTOList = memberGraphRepository.findTotalUserCount();
        return  memberGraphDTOList.stream()
                .map(MemberGraph::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MemberGraphDTO> getMonthTotalUserCount() {
        List<MemberGraph> memberGraphDTOList = memberGraphRepository.findMonthTotalUserCount();
        return   memberGraphDTOList.stream()
                .map(MemberGraph::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MemberGraphDTO> getMonthlyUserCount() {
        List<MemberGraph> memberGraphDTOList = memberGraphRepository.findMonthUserCount();
        return memberGraphDTOList.stream()
                .map(MemberGraph::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<MemberDTO> searchAll(Pageable pageable, String searchKeyword, String searchCondition) {
        Page<Member> memberList = memberRepository.findAll(pageable);
        if (searchKeyword.equals("")) {
            memberList = memberRepository.findByUserNicknameContaining(pageable, searchCondition);
            Page<MemberDTO> memberPageList = memberList.map(Member::toDTO);
            return memberPageList;
        } else {
            memberList = memberRepository.findByRoleContainingAndUserNicknameContaining(pageable, searchCondition, searchKeyword);
            Page<MemberDTO> memberPageList = memberList.map(Member::toDTO);
            return memberPageList;
        }
    }

}
