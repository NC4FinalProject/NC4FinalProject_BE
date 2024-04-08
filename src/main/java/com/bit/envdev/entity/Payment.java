package com.bit.envdev.entity;

import com.bit.envdev.dto.PaymentDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder


public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long paymentId;

    @Column
    private long totalPrice;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date paymentDate;

    // 토스페이먼츠 결제를 위한 컬럼
    @Column
    private String paymentUniqueNo;

    @ManyToOne

    @JoinColumn(name = "member_id")

    private Member member;

    private Long contentsId; // 서비스단에서 수동으로 set하기

    public PaymentDTO toDTO() {
        return PaymentDTO.builder()
                .paymentId(this.paymentId)
                .totalPrice(this.totalPrice)
                .paymentDate(this.paymentDate)
                .paymentUniqueNo(this.paymentUniqueNo)

                .memberDTO(this.member.toDTO())

                .build();
    }
}
