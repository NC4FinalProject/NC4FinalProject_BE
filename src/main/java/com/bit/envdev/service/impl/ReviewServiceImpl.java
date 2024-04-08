package com.bit.envdev.service.impl;

import com.bit.envdev.dto.ReviewDTO;
import com.bit.envdev.entity.*;
import com.bit.envdev.repository.ContentsRepositoty;
import com.bit.envdev.repository.ReviewRepository;
import com.bit.envdev.service.ReviewService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@ToString
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ContentsRepositoty contentsRepositoty;

    @Override
    public List<ReviewDTO> post(ReviewDTO reviewDTO, int contentsId, CustomUserDetails customUserDetails) {

        reviewDTO.setMemberDTO(customUserDetails.getMember().toDTO());

        Contents contents = contentsRepositoty.findById(reviewDTO.getContentsId())
                .orElseThrow(() -> new RuntimeException("컨텐츠가 존재하지 않습니다."));

        reviewRepository.post(reviewDTO.toEntity(contents));

        return reviewRepository.findByContentsContentsIdOrderByReviewUdtDateDesc(contentsId).stream()
                .map(Review::toDTO)
                .collect(Collectors.toList());

    }

    @Override
    public List<ReviewDTO> modify(ReviewDTO reviewDTO, int contentsId, CustomUserDetails customUserDetails) {

        System.out.println("reviewDTO = " + reviewDTO);

        Review review = reviewRepository.findById(reviewDTO.getReviewId())
                .orElseThrow(() -> new RuntimeException("리뷰가 존재하지 않습니다."));

        Contents contents = contentsRepositoty.findById(reviewDTO.getContentsId())
                .orElseThrow(() -> new RuntimeException("컨텐츠가 존재하지 않습니다."));

        ReviewDTO modifyReviewDTO = review.toDTO();
        modifyReviewDTO.setMemberDTO(customUserDetails.getMember().toDTO());
        modifyReviewDTO.setReviewContent(reviewDTO.getReviewContent());
        modifyReviewDTO.setReviewRating(reviewDTO.getReviewRating());



        reviewRepository.save(modifyReviewDTO.toEntity(contents));

        return reviewRepository.findByContentsContentsIdOrderByReviewUdtDateDesc(contentsId).stream()
                .map(Review::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewDTO> delete(long reviewId, int contentsId, CustomUserDetails customUserDetails) {

        reviewRepository.deleteById(reviewId);

        return reviewRepository.findByContentsContentsIdOrderByReviewUdtDateDesc(contentsId).stream()
                .map(Review::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewDTO> getReviewList(int contentsId) {

        return reviewRepository.findByContentsContentsIdOrderByReviewUdtDateDesc(contentsId).stream()
                .map(Review::toDTO)
                .collect(Collectors.toList());
    }
}