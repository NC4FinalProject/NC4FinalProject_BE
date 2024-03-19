package com.bit.envdev.dto;

import com.bit.envdev.entity.Member;
import com.bit.envdev.entity.Review;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDTO {
    private long reviewId;
    private String userNickname;
    private String reviewContent;
    private String reviewRegdate;
    private String reviewModidate;
    private String reviewRating;

    public Review toEntity(Member member) {
        return Review.builder()
                .reviewId(this.reviewId)
                .member(member)
                .reviewContent(this.reviewContent)
                .reviewRegdate(this.reviewRegdate)
                .reviewModidate(this.reviewModidate)
                .reviewRating(this.reviewRating)
                .build();
    }

}
