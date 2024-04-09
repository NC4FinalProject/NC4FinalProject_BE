package com.bit.envdev.dto;

import com.bit.envdev.entity.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ReviewDTO {
    private long reviewId;
    private String reviewContent;
    private Date reviewCrtDate;
    private Date reviewUdtDate;
    private double reviewRating;
    private int contentsId;
    private MemberDTO memberDTO;



    public Review toEntity() {
        return Review.builder()
                .reviewId(this.reviewId)
                .reviewContent(this.reviewContent)
                .reviewCrtDate(this.reviewCrtDate)
                .reviewUdtDate(this.reviewUdtDate)
                .reviewRating(this.reviewRating)
                .member(this.memberDTO.toEntity())
                .contentsId(this.contentsId)
                .build();
    }

}
