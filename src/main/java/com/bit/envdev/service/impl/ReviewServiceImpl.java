package com.bit.envdev.service.impl;

import com.bit.envdev.dto.ReviewDTO;
import com.bit.envdev.entity.CustomUserDetails;
import com.bit.envdev.entity.Review;
import com.bit.envdev.repository.ReviewRepository;
import com.bit.envdev.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;

    @Override
    public List<ReviewDTO> post(ReviewDTO reviewDTO) {

        reviewRepository.save(reviewDTO.toEntity());

        return null;
    }

    @Override
    public List<ReviewDTO> modify(ReviewDTO reviewDTO, CustomUserDetails customUserDetails) {



//        reviewRepository.modify(reviewDTO.toEntity());
//
//        return reviewRepository.findAll().stream()
//                .map(Review::toDTO)
//                .collect(Collectors.toList());
        return null;
  }
}