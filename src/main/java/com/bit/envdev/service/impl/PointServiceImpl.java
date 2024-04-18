package com.bit.envdev.service.impl;

import com.bit.envdev.dto.PointDTO;
import com.bit.envdev.entity.Member;
import com.bit.envdev.entity.Point;
import com.bit.envdev.repository.PointRepository;
import com.bit.envdev.service.PointService;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PointServiceImpl implements PointService {

    private final PointRepository pointRepository;

    @Override
    public void pointJoinWithDTO(PointDTO pointDTO){

        Point point = pointDTO.toEntity();
        pointRepository.save(point);
    }

    @Override
    public void pointJoinWithBuilder(Member member, int value, String reason){
        
        PointDTO pointDTO = PointDTO.builder()
                .member(member)
                .value(value)
                .reason(reason)
                .createdAt(LocalDate.now().toString())
                .build();

        Point point = pointDTO.toEntity();
        try {
            pointRepository.save(point);
        } catch (Exception e) {
            System.out.println("포인트 추가 실패" + e.getMessage());
        }
    }

    @Override
    public long getMyPoint(long memberId) {
        return pointRepository.getMyPoint(memberId);
    }
}
