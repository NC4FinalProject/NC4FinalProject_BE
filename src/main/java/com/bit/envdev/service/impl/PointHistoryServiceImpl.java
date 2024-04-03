package com.bit.envdev.service.impl;


import com.bit.envdev.dto.PointHistoryDTO;
import com.bit.envdev.entity.PointHistory;
import com.bit.envdev.repository.PointHistoryRepository;
import com.bit.envdev.service.PointHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PointHistoryServiceImpl implements PointHistoryService {

    private final PointHistoryRepository pointHistoryRepository;
    @Override
    public void pointHistoryJoin(PointHistoryDTO pointHistoryDTO ) {
        PointHistory pointHistory = pointHistoryDTO.toEntity();

        pointHistoryRepository.save(pointHistory);
    }

    @Override
    public void setPointHistory(int point, String username, String pointCategory){
        pointHistoryRepository.setPointHistory(point, username, pointCategory);
    }

    @Override
    public List<PointHistory> getPointHistory(String username){
        return pointHistoryRepository.getPointHistory(username);
    }

    @Override
    public void pointHistoryRemove(String username) {
        pointHistoryRepository.deleteByUsername(username);
    }

//    @Override
//    public List<PointHistoryDTO> findByUsername(String username) {
//        List<PointHistory> PointHistoryList = pointHistoryRepository.findByMemberUsername(username);
//
//        return PointHistoryList.stream().map(PointHistory -> PointHistory.toDTO()).toList();
//    }
}
