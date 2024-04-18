package com.bit.envdev.service;

import com.bit.envdev.dto.PointDTO;
import com.bit.envdev.entity.Member;

public interface PointService {

    void pointJoinWithDTO (PointDTO pointDTO);

    void pointJoinWithBuilder (Member member, int point,  String reason);

    long getMyPoint(long memberId);
}
