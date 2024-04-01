package com.bit.envdev.service.impl;


import com.bit.envdev.dto.PointDTO;
import com.bit.envdev.entity.Point;
import com.bit.envdev.repository.PointRepository;
import com.bit.envdev.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PointServiceImpl implements PointService {

    private final PointRepository pointRepository;

    @Override
    public void pointJoin(PointDTO pointDTO){
        Point point = pointDTO.toEntity();

        pointRepository.save(point);

    }
    @Override
    public PointDTO getPoint(String username) {
        Optional<Point> optionalPoint = pointRepository.findPointByUsername(username);
        return optionalPoint.map(Point::toDTO).orElse(null);
    }

    @Override
    public void pointCharge(int point, String username) {
        pointRepository.pointCharge(point, username);
    }

    @Override
    public void pointRemove(String username) {
        
        pointRepository.deleteByUsername(username);
    }

}
