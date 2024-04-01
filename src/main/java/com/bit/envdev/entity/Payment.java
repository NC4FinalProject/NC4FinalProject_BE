package com.bit.envdev.entity;

import com.bit.envdev.dto.PaymentDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@IdClass(PaymentId.class)
@SequenceGenerator(
        name = "paymentSeqGenerator",
        sequenceName = "T_PAYMENT_SEQ",
        initialValue = 1,
        allocationSize = 1
)

public class Payment {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "paymentSeqGenerator"
    )
    private long paymentId;

    @Id
    @OneToOne
    @JoinColumns({
            @JoinColumn(name = "cart_id", referencedColumnName = "cart_id"),
            @JoinColumn(name = "lecture_id", referencedColumnName = "lecture_id")
    })
    private CartLecture cartLecture;

    @Column
    private long totalPrice;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date paymentDate;

    // 토스페이먼츠 결제를 위한 컬럼
    @Column
    private String paymentUniqueNo;

    @OneToOne(mappedBy = "payment", cascade = CascadeType.ALL)
    private Review review;

    public PaymentDTO toDTO() {
        return PaymentDTO.builder()
                .paymentId(this.paymentId)
                .cartId(this.cartLecture.getCart().getCartId())
                .lectureId(this.cartLecture.getLecture().getLectureId())
                .totalPrice(this.totalPrice)
                .paymentDate(this.paymentDate)
                .paymentUniqueNo(this.paymentUniqueNo)
                .reviewId(this.review.getReviewId())
                .build();
    }
}