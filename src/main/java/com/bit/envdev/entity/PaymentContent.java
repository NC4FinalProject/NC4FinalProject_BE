package com.bit.envdev.entity;

import com.bit.envdev.dto.PaymentContentDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class PaymentContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private int contentsId; // 수동셋팅
    @ManyToOne
    @JoinColumn(name = "payment_id")
    @JsonBackReference
    private Payment payment;

    public PaymentContentDTO toDTO() {

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
