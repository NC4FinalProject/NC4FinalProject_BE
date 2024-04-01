package com.bit.envdev.service;
import com.bit.envdev.dto.PointHistoryDTO;
import com.bit.envdev.entity.PointHistory;

import java.util.List;

public interface PointHistoryService {

    void pointHistoryJoin (PointHistoryDTO pointHistoryDTO);

    void setPointHistory(int point, String username, String pointCategory);

    List<PointHistory> getPointHistory(String username);

    void pointHistoryRemove(String username);

//    List<PointHistoryDTO> findByUsername(String username);
}
