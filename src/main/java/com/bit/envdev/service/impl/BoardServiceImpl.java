package com.bit.envdev.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bit.envdev.dto.BoardDTO;
import com.bit.envdev.entity.Board;
import com.bit.envdev.entity.Member;
import com.bit.envdev.repository.BoardRepository;
import com.bit.envdev.repository.MemberRepository;
import com.bit.envdev.service.BoardService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    @Override
    public void save(BoardDTO boardDTO) {
        Member loginMember = memberRepository.findByUsername(boardDTO.getUsername()).orElseThrow();

        boardRepository.save(boardDTO.toEntity(loginMember));
    }

    @Override
    public List<BoardDTO> findByUsername(String username) {
        List<Board> boardList = boardRepository.findByMemberUsername(username);

        return boardList.stream().map(board -> board.toDTO()).toList();
    }

    @Override
    public void modify(BoardDTO boardDTO) {
        Member loginMember = memberRepository.findByUsername(boardDTO.getUsername()).orElseThrow();

        boardRepository.save(boardDTO.toEntity(loginMember));
    }

    @Override
    public void remove(long id) {
        boardRepository.deleteById(id);
    }

}
