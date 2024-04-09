package com.bit.envdev.entity;

import com.bit.envdev.dto.PaymentContentDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class PaymentContent {
    @Id
    private int paymentContentId;
    @Column
    private String teacherName;
    @Column
    private String contentsTitle;
    @Column
    private String thumbnail;
    @Column
    private int price;
    @Column
    private int contentsId;

    public PaymentContentDTO toDTO () {
        return PaymentContentDTO.builder()
                .paymentContentId(this.paymentContentId)
                .teacherName(this.teacherName)
                .contentsTitle(this.contentsTitle)
                .thumbnail(this.thumbnail)
                .price(this.price)
                .contentsId(this.contentsId)
                .build();
    }

}
