package com.bit.envdev.dto;

import com.bit.envdev.entity.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDTO {
    private long paymentId;
    private long totalPrice;
    private Date paymentDate;
    private String paymentUniqueNo;
    private long reviewId;
    private long cartId;
    private long lectureId;

    public Payment toEntity() {
        return Payment.builder()
                .paymentId(this.paymentId)
                .review(Review.builder().reviewId(this.reviewId).build())
                .cartLecture(CartLecture.builder()
                        .cart(Cart.builder().cartId(this.cartId).build())
                        .lecture(Lecture.builder().lectureId(this.lectureId).build())
                        .build())
                .totalPrice(this.totalPrice)
                .paymentDate(this.paymentDate)
                .paymentUniqueNo(this.paymentUniqueNo)
                .build();
    }
}

