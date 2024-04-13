package com.bit.envdev.service.impl;

import com.bit.envdev.common.FileUtils;
import com.bit.envdev.dto.NoticeDTO;
import com.bit.envdev.entity.Notice;
import com.bit.envdev.entity.NoticeFile;
import com.bit.envdev.repository.NoticeFileRepository;
import com.bit.envdev.repository.NoticeRepository;
import com.bit.envdev.service.NoticeService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {
    private final NoticeRepository noticeRepository;
    private final FileUtils fileUtils;
    private final NoticeFileRepository noticeFileRepository;

    @Override
    public Page<NoticeDTO> searchAll(Pageable pageable, String searchCondition, String searchKeyword) {
        Page<Notice> noticePage = noticeRepository.searchAll(pageable, searchCondition, searchKeyword);

        return noticePage.map(notice -> notice.toDTO());
    }

    @Override
    @Transactional
    public void post(NoticeDTO noticeDTO) {
        try {
            noticeDTO.setNoticeDate(LocalDateTime.now());

            Notice notice = noticeDTO.toEntity();

            List<NoticeFile> noticeFileList = noticeDTO.getNoticeFileDTOList().stream()
                    .map(fileDTO -> fileDTO.toEntity(notice))
                    .collect(Collectors.toList());

            // Notice 엔티티에 파일들을 추가
            notice.setNoticeFileList(noticeFileList);

            noticeRepository.save(notice);
        } catch (Exception e) {
            throw new RuntimeException("Failed to post notice: " + e.getMessage());
        }
    }


    @Override
    public NoticeDTO findById(Long noticeNo) {
        Notice notice = noticeRepository.findById(noticeNo).orElseThrow();
        return notice.toDTO();
    }

    @Transactional
    public void updateView(Long noticeNo, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        Set<Long> viewedNotices = (Set<Long>) session.getAttribute("viewedNotices");
        if (viewedNotices == null) {
            viewedNotices = new HashSet<>();
        }

        if (!viewedNotices.contains(noticeNo)) {
            this.noticeRepository.updateView(noticeNo);
            viewedNotices.add(noticeNo);
            session.setAttribute("viewedNotices", viewedNotices);
        }
        if (!viewedNotices.contains(noticeNo)) {
            this.noticeRepository.updateView(noticeNo);
            viewedNotices.add(noticeNo);
            session.setAttribute("viewedNotices", viewedNotices);

            Cookie cookie = new Cookie("viewedNotice_" + noticeNo, "true");
            cookie.setMaxAge(60 * 60 * 24);
            response.addCookie(cookie);
        }

    }

    @Override
    @Transactional
    public void modifyNoticeFile(List<Long> modifyNoticeFileLIst) {
        System.out.println(modifyNoticeFileLIst );
        modifyNoticeFileLIst.forEach(noticeFileId -> {
            noticeFileRepository.deleteById(noticeFileId);
            noticeFileRepository.flush();
        });
    }

    @Override
    public List<NoticeDTO> findAll() {
        List<Notice> notices = noticeRepository.findTop4ByOrderByNoticeDateDesc();
        return notices.stream()
                .map(Notice::toDTO)
                .collect(Collectors.toList());
    }


    @Override
    public void removeImage(List<String> temporaryImage) {
        if (temporaryImage != null || temporaryImage.size() > 0) {
            temporaryImage.forEach(image -> {
                String imgName = image.replace("https://kr.object.ncloudstorage.com/envdev/", "");
                fileUtils.deleteObject(image);
            });
        }
    }

    @Override
    public void deleteById(Long noticeNo) {
        Notice notice = noticeRepository.findById(noticeNo).orElseThrow(() -> new RuntimeException("Notice not found"));
        List<NoticeFile> noticeFiles = notice.getNoticeFileList();
        if (noticeFiles != null || noticeFiles.size() > 0) {
            noticeFiles.forEach(noticeFile -> {
                String imgName = noticeFile.getItemFilePath() + noticeFile.getItemFileName();
                fileUtils.deleteObject(imgName);
            });
            noticeRepository.deleteById(noticeNo);
        }
    }

    @Override
    @Transactional
    public void modify(NoticeDTO noticeDTO) {
        Notice notice = noticeRepository.findById(noticeDTO.getId()).orElseThrow(() -> new RuntimeException("Notice not found"));

        try {
            List<NoticeFile> existingFiles = notice.getNoticeFileList();
            List<NoticeFile> noticeFileList = noticeDTO.getNoticeFileDTOList().stream()
                    .map(fileDTO -> fileDTO.toEntity(notice))
                    .collect(Collectors.toList());

            // 기존의 파일 리스트에 새로운 파일 리스트를 추가
            existingFiles.addAll(noticeFileList);
            notice.setNoticeFileList(existingFiles);


            notice.setNoticeContent(noticeDTO.getNoticeContent());
            notice.setNoticeDate(LocalDateTime.now());

            noticeRepository.save(notice);
        } catch (Exception e) {
            throw new RuntimeException("Failed to post notice: " + e.getMessage());
        }
    }

    @Override
   public List<Long> modifyNoticeFileList(NoticeDTO noticeDTO) {
        Notice notice = noticeRepository.findById(noticeDTO.getId()).orElseThrow(() -> new RuntimeException("Notice not found"));
        try {
            String noticeContent = noticeDTO.getNoticeContent();

            System.out.println("notice.getNoticeFileList() : " + noticeContent);
            notice.getNoticeFileList().forEach(noticeFile -> {
            System.out.println("notice.getNoticeFileList() : " + noticeFile.getItemFilePath() + noticeFile.getItemFileName());
            });

            List<Long> fileIdsToDelete = new ArrayList<>();

            for (NoticeFile noticeFile : notice.getNoticeFileList()) {
                if (noticeContent.contains(noticeFile.getItemFilePath() + noticeFile.getItemFileName())) {

                } else {
                    fileUtils.deleteObject(noticeFile.getItemFilePath() + noticeFile.getItemFileName());
                    fileIdsToDelete.add(noticeFile.getItemFileId());
                }
            }

            return fileIdsToDelete;

        } catch (Exception e) {
            throw new RuntimeException("Failed to post notice: " + e.getMessage());
        }
    }
}