package com.bit.envdev.service.impl;

import com.bit.envdev.common.FileUtils;
import com.bit.envdev.dto.InquiryCommentDTO;
import com.bit.envdev.dto.InquiryDTO;
import com.bit.envdev.dto.InquiryFileDTO;
import com.bit.envdev.dto.TagDTO;
import com.bit.envdev.entity.Inquiry;
import com.bit.envdev.entity.InquiryFile;
import com.bit.envdev.entity.Member;
import com.bit.envdev.entity.Tag;
import com.bit.envdev.repository.*;
import com.bit.envdev.service.InquiryService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
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
    private final InquiryCommentRepository inquiryCommentRepository;
    private final MemberRepository memberRepository;
    private final InquiryLikeRepository inquiryLikeRepository;
    private final ContentsRepository contentsRepository;
    private final InquiryCommentLikeRepository inquiryCommentLikeRepository;

    @Override
    public Page<InquiryDTO> searchAll(Pageable pageable, String searchCondition, String searchKeyword, int contentsId) {
        Page<Inquiry> inquiryPage = inquiryRepository.searchAllByContentsId(pageable, searchCondition, searchKeyword, contentsId);

        return inquiryPage.map(inquiry -> {
            InquiryDTO inquiryDTO = inquiry.toDTO();
            List<InquiryCommentDTO> commentDTOList = inquiryDTO.getInquiryCommentDTOList();
            commentDTOList.forEach(
                    inquiryCommentDTO ->
                            inquiryCommentDTO.setInquiryCommentLikeCount(
                                    inquiryCommentLikeRepository.countByInquiryCommentInquiryCommentId(inquiryCommentDTO.getInquiryCommentId())
            ));
            long commentCount = inquiryCommentRepository.countByInquiryInquiryId(inquiry.getInquiryId());
            long likeCount = inquiryLikeRepository.countByInquiryInquiryId(inquiry.getInquiryId());
            String contentsTitle= contentsRepository.findById(inquiry.getContentsId()).orElseThrow().getContentsTitle();
            String author = contentsRepository.findById(inquiry.getContentsId()).orElseThrow().getMember().getUserNickname();
            long memberLikeCount = inquiryLikeRepository.countByMemberMemberIdAndInquiryInquiryId(inquiry.getMember().getMemberId(), inquiryDTO.getInquiryId());
            if(memberLikeCount == 0) {
                inquiryDTO.setLike(false);
            } else {
                inquiryDTO.setLike(true);
            }
            inquiryDTO.setContentsTitle(contentsTitle);
            inquiryDTO.setCommentCount(commentCount);
            inquiryDTO.setLikeCount(likeCount);
            inquiryDTO.setAuthor(author);
            inquiryDTO.getMemberDTO().setPassword("");
            return inquiryDTO;
        });
    }

    @Override
    public void post(InquiryDTO inquiryDTO, long memberId) {
        try {
            Member member = memberRepository.findById(memberId).orElseThrow();

            inquiryDTO.setMemberDTO(member.toDTO());

            Inquiry inquiry = inquiryDTO.toEntity();

            List<InquiryFileDTO> inquiryFileDTOList = inquiryDTO.getInquiryFileDTOList();

            if (inquiryFileDTOList != null) {
                for (InquiryFileDTO fileDTO : inquiryFileDTOList) {
                    InquiryFile inquiryFile = fileDTO.toEntity(inquiry);
                    inquiry.getInquiryFileList().add(inquiryFile);
                }
            }

            List<TagDTO> tagDTOList = inquiryDTO.getTagDTOList();
            if (tagDTOList != null) {
                for (TagDTO tagDTO : tagDTOList) {
                    Tag tag = tagDTO.toEntity(inquiry);
                    inquiry.getTagList().add(tag);
                }
            }

            inquiryRepository.save(inquiry);
        } catch (Exception e) {
            throw new RuntimeException("Failed to post inquiry: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    @Modifying
    public InquiryDTO modify(InquiryDTO inquiryDTO, long memberId) {
        try {
            Inquiry inquiry = inquiryRepository.findById(inquiryDTO.getInquiryId()).orElseThrow();

            Inquiry modifyInquiry = Inquiry.builder()
                    .inquiryId(inquiryDTO.getInquiryId())
                    .isPrivate(inquiryDTO.isPrivate())
                    .inquiryTitle(inquiryDTO.getInquiryTitle())
                    .inquiryContent(inquiryDTO.getInquiryContent())
                    .member(inquiry.getMember())
                    .inquiryCrtDT(inquiry.getInquiryCrtDT())
                    .inquiryUdtDT(inquiry.getInquiryUdtDT())
                    .inquiryView(inquiry.getInquiryView())
                    .contentsId(inquiry.getContentsId())
                    .inquiryCommentList(inquiry.getInquiryCommentList())
                    .inquiryFileList(inquiry.getInquiryFileList())
                    .tagList(inquiry.getTagList())
                    .isSolved(inquiry.isSolved())
                    .build();

            List<InquiryFileDTO> inquiryFileDTOList = inquiryDTO.getInquiryFileDTOList();
            if (inquiryFileDTOList != null) {
                for (InquiryFileDTO fileDTO : inquiryFileDTOList) {
                    InquiryFile inquiryFile = fileDTO.toEntity(modifyInquiry);
                    modifyInquiry.getInquiryFileList().add(inquiryFile);
                }
            }
            return inquiryRepository.save(modifyInquiry).toDTO();
        } catch (Exception e) {
            throw new RuntimeException("Failed to post inquiry: " + e.getMessage());
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
    public InquiryDTO updateInquiryView(Long inquiryId) {
        InquiryDTO inquiryDTO = inquiryRepository.findById(inquiryId).orElseThrow().toDTO();

        inquiryDTO.setInquiryView(inquiryDTO.getInquiryView() + 1);

        Inquiry inquiry = inquiryDTO.toEntity();

        List<InquiryFileDTO> inquiryFileDTOList = inquiryDTO.getInquiryFileDTOList();

        if (inquiryFileDTOList != null) {
            for (InquiryFileDTO fileDTO : inquiryFileDTOList) {
                InquiryFile inquiryFile = fileDTO.toEntity(inquiry);
                inquiry.getInquiryFileList().add(inquiryFile);
            }
        }

        List<TagDTO> tagDTOList = inquiryDTO.getTagDTOList();

        if (tagDTOList != null) {
            for (TagDTO tagDTO : tagDTOList) {
                Tag tag = tagDTO.toEntity(inquiry);
                inquiry.getTagList().add(tag);
            }
        }

        InquiryDTO returnInquiryDTO = inquiryRepository.save(inquiry).toDTO();
        List<InquiryCommentDTO> commentDTOList = returnInquiryDTO.getInquiryCommentDTOList();
        commentDTOList.forEach(
                inquiryCommentDTO ->
                        inquiryCommentDTO.setInquiryCommentLikeCount(
                                inquiryCommentLikeRepository.countByInquiryCommentInquiryCommentId(inquiryCommentDTO.getInquiryCommentId())
                        ));
        long commentCount = inquiryCommentRepository.countByInquiryInquiryId(returnInquiryDTO.getInquiryId());
        long likeCount = inquiryLikeRepository.countByInquiryInquiryId(returnInquiryDTO.getInquiryId());
        long memberLikeCount = inquiryLikeRepository.countByMemberMemberIdAndInquiryInquiryId(inquiry.getMember().getMemberId(), inquiryDTO.getInquiryId());
        if(memberLikeCount == 0) {
            inquiryDTO.setLike(false);
        } else {
            inquiryDTO.setLike(true);
        }
        String contentsTitle= contentsRepository.findById(returnInquiryDTO.getContentsId()).orElseThrow().getContentsTitle();
        String author = contentsRepository.findById(returnInquiryDTO.getContentsId()).orElseThrow().getMember().getUserNickname();
        returnInquiryDTO.setContentsTitle(contentsTitle);
        returnInquiryDTO.setCommentCount(commentCount);
        returnInquiryDTO.setLikeCount(likeCount);
        returnInquiryDTO.setAuthor(author);

        return returnInquiryDTO;
    }

    @Override
    public Page<InquiryDTO> myInquiries(Pageable pageable, String searchCondition, String searchKeyword, int contentsId, long memberId) {
        Page<Inquiry> inquiryPage = inquiryRepository.myInquiries(pageable, searchCondition, searchKeyword, contentsId, memberId);

        return inquiryPage.map(inquiry -> {
            InquiryDTO inquiryDTO = inquiry.toDTO();
            List<InquiryCommentDTO> commentDTOList = inquiryDTO.getInquiryCommentDTOList();
            commentDTOList.forEach(
                    inquiryCommentDTO ->
                            inquiryCommentDTO.setInquiryCommentLikeCount(
                                    inquiryCommentLikeRepository.countByInquiryCommentInquiryCommentId(inquiryCommentDTO.getInquiryCommentId())
                            ));
            long commentCount = inquiryCommentRepository.countByInquiryInquiryId(inquiry.getInquiryId());
            long likeCount = inquiryLikeRepository.countByInquiryInquiryId(inquiry.getInquiryId());
            String contentsTitle= contentsRepository.findById(inquiry.getContentsId()).orElseThrow().getContentsTitle();
            String author = contentsRepository.findById(inquiry.getContentsId()).orElseThrow().getMember().getUserNickname();
            long memberLikeCount = inquiryLikeRepository.countByMemberMemberIdAndInquiryInquiryId(inquiry.getMember().getMemberId(), inquiryDTO.getInquiryId());
            if(memberLikeCount == 0) {
                inquiryDTO.setLike(false);
            } else {
                inquiryDTO.setLike(true);
            }
            inquiryDTO.setContentsTitle(contentsTitle);
            inquiryDTO.setCommentCount(commentCount);
            inquiryDTO.setLikeCount(likeCount);
            inquiryDTO.setAuthor(author);
            return inquiryDTO;
        });
    }

    @Override
    public InquiryDTO upadateSolve(long inquiryId) {
        Inquiry inquiry = inquiryRepository.findById(inquiryId).orElseThrow();

        Inquiry solvedInquiry = Inquiry.builder()
                .inquiryId(inquiryId)
                .isSolved(true)
                .inquiryCrtDT(inquiry.getInquiryCrtDT())
                .inquiryUdtDT(inquiry.getInquiryUdtDT())
                .inquiryView(inquiry.getInquiryView())
                .contentsId(inquiry.getContentsId())
                .inquiryContent(inquiry.getInquiryContent())
                .tagList(inquiry.getTagList())
                .inquiryFileList(inquiry.getInquiryFileList())
                .inquiryCommentList(inquiry.getInquiryCommentList())
                .member(inquiry.getMember())
                .inquiryTitle(inquiry.getInquiryTitle())
                .isPrivate(inquiry.isPrivate())
                .build();

        return inquiryRepository.save(solvedInquiry).toDTO();
    }

    @Override
    public String getContentsTitle(int contentsId) {
        return contentsRepository.findById(contentsId).orElseThrow().getContentsTitle();
    }

    @Override
    public String getContentsAuthor(int contentsId) {
        return contentsRepository.findById(contentsId).orElseThrow().getMember().getUserNickname();
    }

    @Override
    public long getLikeCount(long inquiryId) {
        return inquiryLikeRepository.countByInquiryInquiryId(inquiryId);
    }

    @Override
    public long countByMemberId(Member entity) {
        return inquiryLikeRepository.countByMember(entity);
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
                String imgName = image.replace("https://kr.object.ncloudstorage.com/envdev/", "");
                fileUtils.deleteObject(imgName);
            });
        }
    }

    @Override
    public void deleteById(Long inquiryId) {
        inquiryRepository.deleteById(inquiryId);
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
