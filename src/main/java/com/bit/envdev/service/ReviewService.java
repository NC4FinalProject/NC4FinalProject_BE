package com.bit.envdev.service;

import com.bit.envdev.dto.ReviewDTO;
import com.bit.envdev.entity.CustomUserDetails;
import com.bit.envdev.entity.Review;

import java.util.List;

public interface ReviewService {

    List<ReviewDTO> post(ReviewDTO reviewDTO, CustomUserDetails customUserDetails);

    List<ReviewDTO> modify(ReviewDTO reviewDTO, CustomUserDetails customUserDetails);

    List<ReviewDTO> delete(long reviewId, long contentsId, CustomUserDetails customUserDetails);

    List<ReviewDTO> getReviewList(long contentsId);


}
