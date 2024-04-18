package com.bit.envdev.service.impl;


import com.bit.envdev.constant.Role;
import com.bit.envdev.dto.EmailVerifyMemberDTO;
import com.bit.envdev.dto.MemberDTO;
import com.bit.envdev.dto.MemberGraphDTO;
import com.bit.envdev.entity.Member;
import com.bit.envdev.entity.MemberGraph;
import com.bit.envdev.jwt.JwtTokenProvider;
import com.bit.envdev.repository.MemberGraphRepository;
import com.bit.envdev.repository.MemberRepository;
import com.bit.envdev.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final MemberGraphRepository memberGraphRepository;

    @Override
    public Member join(MemberDTO memberDTO) {
        memberDTO.setPassword(passwordEncoder.encode(memberDTO.getPassword()));

        if(memberDTO.getUserNickname() == null) {
            memberDTO.setUserNickname(memberDTO.getUsername());
        }

        //유효성 검사
        if (memberDTO.getUsername() == null || memberDTO.getPassword() == null) {
            throw new RuntimeException("Invalid Argument");
        }

        //username 중복 체크
        if (memberRepository.existsByUsername(memberDTO.getUsername())) {
            throw new RuntimeException("already exist username");
        }
        
        memberDTO.setCreatedAt(LocalDateTime.now().toString());
        memberDTO.setModifiedAt(LocalDateTime.now().toString());
        memberDTO.setRole(com.bit.envdev.constant.Role.USER);

        try {
            Member joinMember = memberRepository.save(memberDTO.toEntity());
            return joinMember;
        } catch (Exception e) {
            System.out.println("회원가입 실패" + e.getMessage());
        }

        return memberDTO.toEntity();
    }

    @Override
    public MemberDTO login(MemberDTO memberDTO) {
        // username으로 조회
        Optional<Member> loginMember = memberRepository.findByUsername(memberDTO.getUsername());

        if (loginMember.isEmpty()) {
            throw new RuntimeException("not exist username");
        }

        // 암호화되어 있는 비밀번호와 사용자가 입력한 비밀번호 비교
        if (!passwordEncoder.matches(memberDTO.getPassword(), loginMember.get().getPassword())) {
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

        if (!memberRepository.findByUsername(memberDTO.getUsername()).isEmpty()) {
            throw new RuntimeException("already exist username");
        }

        return memberDTO;
    }

    @Override
    public MemberDTO nicknameCheck(MemberDTO memberDTO) {

        if (!memberRepository.findByUserNickname(memberDTO.getUserNickname()).isEmpty()) {
            throw new RuntimeException("already exist userNickname");
        }

        return memberDTO;
    }

    @Override
    public MemberDTO updateProfile(String fileString, MemberDTO memberDTO) {

        Member NewMember = memberRepository.findByUsername(memberDTO.getUsername()).get();
        memberDTO.setProfileFile(fileString);

        Member updatedMember = Member.builder()
                .memberId(memberDTO.getMemberId())
                .username(memberDTO.getUsername())
                .password(memberDTO.getPassword())
                .userNickname(memberDTO.getUserNickname())
                .role(memberDTO.getRole())
                .profileFile(memberDTO.getProfileFile())
                .emailVerification(memberDTO.getEmailVerification())
                .createdAt(LocalDateTime.parse(memberDTO.getCreatedAt()))
                .modifiedAt(LocalDateTime.now())
                .memo(memberDTO.getMemo())
                .contentsList(memberDTO.toEntity().getContentsList())
                .build();

        Member joinMember = memberRepository.save(updatedMember);
        return joinMember.toDTO();
    }

    @Override
    public MemberDTO findByUsername(String username) {
        MemberDTO memberDTO = new MemberDTO();
        Member newMember = memberRepository.findByUsername(username).orElse(null);
        if (newMember != null) {
            try {
                memberDTO = newMember.toDTO();

                return memberDTO;
            } catch (Exception e) {
                System.out.println("회원정보 조회 실패" + e.getMessage());
            }
        }

        return memberDTO;
    }

    @Override
    public MemberDTO updateUserNickname(String userNickname, MemberDTO memberDTO) {

        MemberDTO NewMemberDTO = memberRepository.findByUsername(memberDTO.getUsername()).get().toDTO();
        memberDTO.setUserNickname(userNickname);

        Member updatedMember = Member.builder()
                .memberId(memberDTO.getMemberId())
                .username(memberDTO.getUsername())
                .password(memberDTO.getPassword())
                .userNickname(memberDTO.getUserNickname())
                .role(memberDTO.getRole())
                .profileFile(memberDTO.getProfileFile())
                .emailVerification(memberDTO.getEmailVerification())
                .createdAt(LocalDateTime.parse(memberDTO.getCreatedAt()))
                .modifiedAt(LocalDateTime.now())
                .memo(memberDTO.getMemo())
                .contentsList(memberDTO.toEntity().getContentsList())
                .build();

        Member joinMember = memberRepository.save(updatedMember);
        return joinMember.toDTO();
    }

    @Override
    public MemberDTO wannabeTeacher(MemberDTO memberDTO) {
        MemberDTO NewMemberDTO = memberRepository.findByUsername(memberDTO.getUsername()).get().toDTO();
        memberDTO.setRole(Role.PRETEACHER);

        Member updatedMember = Member.builder()
                .memberId(memberDTO.getMemberId())
                .username(memberDTO.getUsername())
                .password(memberDTO.getPassword())
                .userNickname(memberDTO.getUserNickname())
                .role(memberDTO.getRole())
                .profileFile(memberDTO.getProfileFile())
                .emailVerification(memberDTO.getEmailVerification())
                .createdAt(LocalDateTime.parse(memberDTO.getCreatedAt()))
                .modifiedAt(LocalDateTime.now())
                .memo(memberDTO.getMemo())
                .contentsList(memberDTO.toEntity().getContentsList())
                .build();

        Member joinMember = memberRepository.save(updatedMember);
        return joinMember.toDTO();
    }

    @Override
    public String codeVerification(String tempCode, String code) {

        if (tempCode.equals(code)) {
            return "correct";
        } else {
            return "incorrect";
        }
    }

    @Override
    public String getProfileImageUrl(String noticeWriter) {
        String profileImageUrl = memberRepository.findByUserNickname(noticeWriter).get().getProfileFile();
        return profileImageUrl;
    }

    @Override
    public List<MemberDTO> find4User() {

        List<Member> members = memberRepository.findTop4ByOrderByMemberIdDesc();

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
    public List<MemberGraphDTO> getMonthTotalUserCount() {
        List<MemberGraph> memberGraphDTOList = memberGraphRepository.findMonthTotalUserCount();
        return memberGraphDTOList.stream()
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
    public Page<MemberDTO> searchAll(Pageable pageable, String searchKeyword, Role role) {
        Page<Member> memberList = memberRepository.findAll(pageable);
        memberList = memberRepository.findByRoleAndUserNicknameContainingOrderByCreatedAtDesc(pageable, role,searchKeyword);
            return memberList.map(Member::toDTO);

    }

    @Override
    public Page<MemberDTO> searchData(Pageable pageable, String searchKeyword) {
        Page<Member> memberList = memberRepository.findAll(pageable);
        memberList = memberRepository.findByUserNicknameContainingOrderByCreatedAtDesc(pageable, searchKeyword);
        return memberList.map(Member::toDTO);
    }

    @Override
    public void updateUserMemo(MemberDTO memberDTO) {
        MemberDTO NewMemberDTO = memberRepository.findById(memberDTO.getMemberId()).get().toDTO();
        memberDTO.setMemo(memberDTO.getMemo());

        Member updatedMember = Member.builder()
                .memberId(memberDTO.getMemberId())
                .username(memberDTO.getUsername())
                .password(memberDTO.getPassword())
                .userNickname(memberDTO.getUserNickname())
                .role(memberDTO.getRole())
                .profileFile(memberDTO.getProfileFile())
                .emailVerification(memberDTO.getEmailVerification())
                .createdAt(LocalDateTime.parse(memberDTO.getCreatedAt()))
                .modifiedAt(LocalDateTime.now())
                .memo(memberDTO.getMemo())
                .contentsList(memberDTO.toEntity().getContentsList())
                .build();

        memberRepository.save(updatedMember);
    }

    @Override
    public long getPreTeacherCount() {
        Role role = Role.PRETEACHER;
        return memberRepository.countByRole(role);
    }

    @Override
    public List<MemberGraphDTO> getDailyOutUserCount() {
        List<MemberGraph> dailyOutUserCount = memberGraphRepository.findDailyOutUserCount();
        return dailyOutUserCount.stream()
                .map(MemberGraph::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MemberGraphDTO> getMonthlyOutUserCount() {
        List<MemberGraph> MonthlyOutUserCount = memberGraphRepository.findMonthlyOutUserCount();
        return MonthlyOutUserCount.stream()
                .map(MemberGraph::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MemberDTO> findByRole() {
        Role role = Role.PRETEACHER;
        List<Member> members = memberRepository.findTop4ByRole(role);
        return members.stream()
                .map(Member::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MemberDTO findById(long id) {
        Member member = memberRepository.findById(id).orElseThrow();
        return member.toDTO();
    }

    @Override
    public long getTodayUserCount() {
        long todayUserCount = memberRepository.countTodayRegistrations();
        return todayUserCount;
    }

    @Override
    public void changePw(MemberDTO member, String userPw) {
        member.setPassword(passwordEncoder.encode(userPw));

        Member updatedMember = Member.builder()
                .memberId(member.getMemberId())
                .username(member.getUsername())
                .password(member.getPassword())
                .userNickname(member.getUserNickname())
                .role(member.getRole())
                .profileFile(member.getProfileFile())
                .emailVerification(member.getEmailVerification())
                .createdAt(LocalDateTime.parse(member.getCreatedAt()))
                .modifiedAt(LocalDateTime.now())
                .memo(member.getMemo())
                .contentsList(member.toEntity().getContentsList())
                .build();

        memberRepository.save(updatedMember);
    }

    @Override
    public void changeRole(MemberDTO memberDTO, Role role) {
        Member member = memberRepository.findById(memberDTO.getMemberId()).orElseThrow();

        Member changedMember = Member.builder()
                .memberId(member.getMemberId())
                .contentsList(member.getContentsList())
                .memo(member.getMemo())
                .role(role)
                .modifiedAt(LocalDateTime.now())
                .createdAt(member.getCreatedAt())
                .emailVerification(member.getEmailVerification())
                .password(member.getPassword())
                .pointList(member.getPointList())
                .profileFile(member.getProfileFile())
                .username(member.getUsername())
                .userNickname(member.getUserNickname())
                .build();

        memberRepository.save(changedMember);
    }

}
