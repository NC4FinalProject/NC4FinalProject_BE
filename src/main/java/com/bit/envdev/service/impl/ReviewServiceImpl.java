package com.bit.envdev.service.impl;

import com.bit.envdev.dto.ReviewDTO;
import com.bit.envdev.entity.*;
import com.bit.envdev.repository.ContentsRepository;
import com.bit.envdev.repository.MemberRepository;
import com.bit.envdev.repository.ReviewRepository;
import com.bit.envdev.service.ReviewService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@ToString
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ContentsRepository contentsRepository;
    private final MemberRepository memberRepository;

    @Override
    public List<ReviewDTO> post(ReviewDTO reviewDTO, CustomUserDetails customUserDetails) {

        reviewDTO.setMemberDTO(memberRepository.findById(customUserDetails.getMember().getMemberId()).orElseThrow().toDTO());

        Optional<Review> existingReview = reviewRepository.findByMemberAndContentsId(customUserDetails.getMember(), reviewDTO.getContentsId());
        if (existingReview.isPresent()) {
            throw new RuntimeException("이미 리뷰를 작성하셨습니다.");
        }
        reviewRepository.post(reviewDTO.toEntity());

        return reviewRepository.findByContentsIdOrderByReviewUdtDateDesc(reviewDTO.getContentsId()).stream()
                .map(review -> {
                    ReviewDTO dto = review.toDTO();
                    dto.getMemberDTO().setPassword("");
                    return dto;
                })
                .collect(Collectors.toList());

    }

    @Transactional
    @Modifying
    @Override
    public List<ReviewDTO> modify(ReviewDTO reviewDTO, CustomUserDetails customUserDetails) {
        reviewDTO.setMemberDTO(memberRepository.findById(customUserDetails.getMember().getMemberId()).orElseThrow().toDTO());
        reviewRepository.save(reviewDTO.toEntity());

        reviewRepository.flush();

        return reviewRepository.findByContentsIdOrderByReviewUdtDateDesc(reviewDTO.getContentsId()).stream()
                .map(modifyReview -> {
                    ReviewDTO dto = modifyReview.toDTO();
                    dto.getMemberDTO().setPassword(null);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewDTO> delete(long reviewId, int contentsId, CustomUserDetails customUserDetails) {

        reviewRepository.deleteById(reviewId);

        return reviewRepository.findByContentsIdOrderByReviewUdtDateDesc(contentsId).stream()
                .map(review -> {
                    ReviewDTO dto = review.toDTO();
                    dto.getMemberDTO().setPassword("");
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewDTO> getReviewList(int contentsId) {

        return reviewRepository.findByContentsIdOrderByReviewUdtDateDesc(contentsId).stream()
                .map(review -> {
                    ReviewDTO dto = review.toDTO();
                    dto.getMemberDTO().setPassword("");
                    return dto;
                })
                .collect(Collectors.toList());
    }
}