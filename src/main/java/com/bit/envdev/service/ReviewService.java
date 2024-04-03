package com.bit.envdev.service;

import com.bit.envdev.dto.ReviewDTO;
import com.bit.envdev.entity.CustomUserDetails;

import java.util.List;

public interface ReviewService {

    List<ReviewDTO> post(ReviewDTO reviewDTO);

    List<ReviewDTO> modify(ReviewDTO reviewDTO, CustomUserDetails customUserDetails);

}
