package com.bit.envdev.service;
import com.bit.envdev.dto.PointDTO;

public interface PointService {
    void pointJoin (PointDTO pointDTO);

    PointDTO getPoint(String username);

    void pointCharge(int point, String username);
}
