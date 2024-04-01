package com.bit.envdev.contentsService.impl;

import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bit.envdev.contentsDTO.ContentsDTO;
import com.bit.envdev.contentsEntity.Contents;
import com.bit.envdev.contentsRepository.ContentsRepositoty;
import com.bit.envdev.contentsService.ContentsService;
import com.bit.envdev.entity.Member;
import com.bit.envdev.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContentsServiceImpl implements ContentsService {

    private final ContentsRepositoty contentsRepository;
    private final MemberRepository memberRepository;

    @Override
    // public void save(ContentsDTO contentsDTO) {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'save'");
    // }

    public Contents createContents(ContentsDTO contentsDTO, Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Contents contents = contentsDTO.toEntity(member);
        return contentsRepository.save(contents); // 생성된 Contents 엔티티 저장
    }








    @Override
    public List<ContentsDTO> findByContents(int contentsId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByContents'");
    }

    @Override
    public List<ContentsDTO> findByAllContents() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByAllContents'");
    }

    @Override
    public void update(ContentsDTO contentsDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }
    
}
