package com.bit.envdev.service.impl;

import com.bit.envdev.common.FileUtils;
import com.bit.envdev.dto.InquiryDTO;
import com.bit.envdev.dto.InquiryFileDTO;
import com.bit.envdev.entity.Inquiry;
import com.bit.envdev.entity.InquiryFile;
import com.bit.envdev.repository.InquiryFileRepository;
import com.bit.envdev.repository.InquiryRepository;
import com.bit.envdev.service.InquiryService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class InquiryServiceImpl implements InquiryService {
    private final InquiryRepository inquiryRepository;
    private final FileUtils fileUtils;
    private final InquiryFileRepository inquiryFileRepository;


    @Override
    public Page<InquiryDTO> searchAll(Pageable pageable, String searchCondition, String searchKeyword) {
        Page<Inquiry> inquiryPage = inquiryRepository.searchAll(pageable, searchCondition, searchKeyword);

        return inquiryPage.map(Inquiry::toDTO);
    }

    @Override
    public void post(InquiryDTO inquiryDTO) {
        try {
            inquiryRepository.save(inquiryDTO.toEntity());
        } catch (Exception e) {
            throw new RuntimeException("Failed to post inquiry: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public void modify(InquiryDTO inquiryDTO) {
        Inquiry inquiry = inquiryRepository.findById(inquiryDTO.getInquiryId()).orElseThrow(() -> new RuntimeException("질의응답이 존재하지 않습니다."));
        InquiryDTO existingInquiryDTO = inquiry.toDTO();

        try {
            List<InquiryFileDTO> existingInquiryFiles = existingInquiryDTO.getInquiryFileDTOList();
            List<InquiryFileDTO> inquiryFileList = inquiryDTO.getInquiryFileDTOList().stream().toList();

            existingInquiryFiles.addAll(inquiryFileList);
            inquiryDTO.setInquiryFileDTOList(existingInquiryFiles);

            inquiryDTO.setInquiryContent(inquiryDTO.getInquiryContent());

            inquiryRepository.save(inquiryDTO.toEntity());

        } catch (Exception e) {
            throw new RuntimeException("질의응답 수정 실패: " + e.getMessage());
        }

    }

    @Override
    @Transactional
    public void modifyInquiryFile(List<Long> modifyInquiryFileLIst) {
        modifyInquiryFileLIst.forEach(inquiryFileId -> {
            inquiryFileRepository.deleteById(inquiryFileId);
            inquiryFileRepository.flush();
        });
    }

    @Override
    public List<Long> modifyInquiryFileList(InquiryDTO inquiryDTO) {
        Inquiry inquiry = inquiryRepository.findById(inquiryDTO.getInquiryId()).orElseThrow(() -> new RuntimeException("질의응답이 존재하지 않습니다."));
        try {
            String inquiryContent = inquiryDTO.getInquiryContent();
            System.out.println("inquiry.getInquiryFileList() : " + inquiryContent);
            inquiry.getInquiryFileList().forEach(inquiryFile -> {
                System.out.println("inquiry.getInquiryFileList() : " + inquiryFile.getInquiryFilePath() + inquiryFile.getInquiryFileName());
            });

            List<Long> inquiryFileIdsToDelete = inquiry.getInquiryFileList().stream()
                    .filter(inquiryFile -> !inquiryContent.contains(inquiryFile.getInquiryFilePath() + inquiryFile.getInquiryFileName()))
                    .peek(inquiryFile -> fileUtils.deleteObject(inquiryFile.getInquiryFilePath() + inquiryFile.getInquiryFileName()))
                    .map(InquiryFile::getInquiryFileId)
                    .toList();


            return inquiryFileIdsToDelete;

        } catch (Exception e) {
            throw new RuntimeException("질의응답 수정 실패: " + e.getMessage());
        }
    }

    @Override
    public void removeImage(List<String> temporaryImage) {
        if (temporaryImage != null || temporaryImage.size() > 0) {
            temporaryImage.forEach(image -> {
                String imgName = image.replace("https://kr.object.ncloudstorage.com/bitcamp-bucket-36/", "");
                fileUtils.deleteObject(imgName);
            });
        }
    }

    @Override
    public void deleteById(Long inquiryId) {
        Inquiry inquiry = inquiryRepository.findById(inquiryId).orElseThrow(() -> new RuntimeException("Inquiry not found"));
        List<InquiryFile> inquiryFiles = inquiry.getInquiryFileList();
        if (inquiryFiles != null || inquiryFiles.size() > 0) {
            inquiryFiles.forEach(inquiryFile -> {
                String imgName = inquiryFile.getInquiryFilePath() + inquiryFile.getInquiryFileName();
                fileUtils.deleteObject(imgName);
            });
            inquiryRepository.deleteById(inquiryId);
        }
    }


    @Override
    public InquiryDTO findById(Long inquiryId) {
        Inquiry inquiry = inquiryRepository.findById(inquiryId).orElseThrow();
        return inquiry.toDTO();
    }

    @Transactional
    @Override
    public void updateView(Long inquiryId, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        Set<Long> viewedInquiries = (Set<Long>) session.getAttribute("viewedInquiries");
        if (viewedInquiries == null) {
            viewedInquiries = new HashSet<>();
        }

        if (!viewedInquiries.contains(inquiryId)) {
            this.inquiryRepository.updateView(inquiryId);
            viewedInquiries.add(inquiryId);
            session.setAttribute("viewedInquiries", viewedInquiries);

            Cookie cookie = new Cookie("viewedNotice_" + inquiryId, "true");
            cookie.setMaxAge(60 * 60 * 24);
            response.addCookie(cookie);
        }

    }

}
