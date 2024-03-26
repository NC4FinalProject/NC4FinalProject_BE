package com.bit.envdev.service.impl;

import com.bit.envdev.dto.NoticeDTO;
import com.bit.envdev.entity.Notice;
import com.bit.envdev.entity.NoticeFile;
import com.bit.envdev.repository.NoticeRepository;
import com.bit.envdev.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {
    private final NoticeRepository noticeRepository;
    @Override
    public Page<NoticeDTO> searchAll(Pageable pageable, String searchCondition, String searchKeyword) {
        Page<Notice> noticePage = noticeRepository.searchAll(pageable, searchCondition, searchKeyword);

        return noticePage.map(notice -> notice.toDTO());
    }

    @Override
    public void post(NoticeDTO noticeDTO) {
        try {
            // 현재 날짜와 시간을 설정
            noticeDTO.setNoticeDate(LocalDateTime.now());

            // NoticeDTO를 Notice 엔티티로 변환
            Notice notice = noticeDTO.toEntity();

            // Notice 엔티티에 연결된 파일들을 생성하고 설정
            List<NoticeFile> noticeFileList = noticeDTO.getNoticeFileDTOList().stream()
                    .map(fileDTO -> fileDTO.toEntity(notice))
                    .collect(Collectors.toList());

            // Notice 엔티티에 파일들을 추가
            notice.setNoticeFileList(noticeFileList);

            noticeRepository.save(notice);
        } catch (Exception e) {
            // 예외 처리
            throw new RuntimeException("Failed to post notice: " + e.getMessage());
        }
    }

    @Override
    public NoticeDTO findById(Long noticeNo) {
        Notice notice = noticeRepository.findById(noticeNo).orElseThrow();

        return notice.toDTO();
    }

}
