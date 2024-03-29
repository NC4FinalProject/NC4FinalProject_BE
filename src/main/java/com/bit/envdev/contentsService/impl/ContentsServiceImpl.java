package com.bit.envdev.contentsService.impl;

import org.springframework.stereotype.Service;

import com.bit.envdev.contentsRepository.ContentsRepositoty;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContentsServiceImpl {
    private final ContentsRepositoty contentsRepositoty;
    
}
