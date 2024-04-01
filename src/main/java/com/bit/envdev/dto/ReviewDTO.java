package com.bit.envdev.dto;

import com.bit.envdev.entity.CartLecture;
import com.bit.envdev.entity.Review;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDTO {
    private long reviewId;
    private String reviewContent;
    private Date reviewCrtDate;
    private Date reviewUdtDate;
    private int reviewRating;


    public Review toEntity() {
        return Review.builder()
                .reviewId(this.reviewId)
                .reviewContent(this.reviewContent)
                .reviewCrtDate(this.reviewCrtDate)
                .reviewUdtDate(this.reviewUdtDate)
                .reviewRating(this.reviewRating)
                .build();
    }

}
