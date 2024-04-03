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
    private long paymentId;
    private long cartId;
    private int contentsId;
    private MemberDTO memberDTO;



    public Review toEntity() {
        return Review.builder()
                .reviewId(this.reviewId)
                .reviewContent(this.reviewContent)
                .reviewCrtDate(this.reviewCrtDate)
                .reviewUdtDate(this.reviewUdtDate)
                .reviewRating(this.reviewRating)
                .payment(Payment.builder()
                        .paymentId(this.paymentId)
                        .cartContents(CartContents.builder()
                                .cart(Cart.builder().cartId(this.cartId).build())
                                .contents(Contents.builder().contentsId(this.contentsId).build())
                                .build())
                        .member(this.memberDTO.toEntity())
                        .build())
                .member(this.memberDTO.toEntity())
                .build();
    }

}
