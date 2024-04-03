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
    private long cartId;
    private long contentsId;
    private long memberId;

    public Payment toEntity() {
        return Payment.builder()
                .paymentId(this.paymentId)
                .cartContents(CartContents.builder()
                        .cart(Cart.builder().cartId(this.cartId).build())
                        .contents(Contents.builder().contentsId(this.contentsId).build())
                        .build())
                .totalPrice(this.totalPrice)
                .paymentDate(this.paymentDate)
                .paymentUniqueNo(this.paymentUniqueNo)
                .member(Member.builder().id(this.memberId).build())
                .build();
    }
}

