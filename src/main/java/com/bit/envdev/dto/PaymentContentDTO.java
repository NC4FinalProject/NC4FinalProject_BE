package com.bit.envdev.dto;


import com.bit.envdev.entity.Payment;
import com.bit.envdev.entity.PaymentContent;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PaymentContentDTO {
    private int paymentContentId;
    private String teacherName;
    private String contentsTitle;
    private String thumbnail;
    private int price;
    private int contentsId;

    public PaymentContent toEntity(Payment payment) {
        return PaymentContent.builder()
                .paymentContentId(this.paymentContentId)
                .teacherName(this.teacherName)
                .contentsTitle(this.contentsTitle)
                .thumbnail(this.thumbnail)
                .price(this.price)
                .payment(payment)
                .contentsId(this.contentsId)
                .build();
    }
}
