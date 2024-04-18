package com.bit.envdev.service;

import com.bit.envdev.dto.ReviewDTO;
import com.bit.envdev.entity.CustomUserDetails;
import com.bit.envdev.entity.Member;

import java.util.List;

public interface ReviewService {

    List<ReviewDTO> post(ReviewDTO reviewDTO, CustomUserDetails customUserDetails);

    List<ReviewDTO> modify(ReviewDTO reviewDTO, CustomUserDetails customUserDetails);

    List<ReviewDTO> delete(long reviewId, int contentsId, CustomUserDetails customUserDetails);

    List<ReviewDTO> getReviewList(int contentsId);
    List<Object[]> get10RecentComments();

    List<Object[]> get12BestContents();

    List<Object[]> get12RecentContents();

    List<Object[]> get12RandomContents();

    long countByMemberId(Member entity);
}
