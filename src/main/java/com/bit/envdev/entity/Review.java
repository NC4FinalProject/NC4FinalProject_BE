package com.bit.envdev.entity;

import com.bit.envdev.dto.ReviewDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.mapping.ToOne;

import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long reviewId;

    @Column(nullable = false)
    private String reviewContent;

    @Column(updatable = false)
    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    private Date reviewCrtDate;

    @Column
    @UpdateTimestamp
    @Temporal(TemporalType.DATE)
    private Date reviewUdtDate;

    @Column(nullable = false)
    private double reviewRating;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumns({
            @JoinColumn(name = "payment_id"),
            @JoinColumn(name = "cart_id"),
            @JoinColumn(name = "contents_id")
    })
    private Payment payment;

    @ManyToOne
    @JoinColumn(name = "id", referencedColumnName = "Id")
    private Member member;

    public ReviewDTO toDTO() {
        return ReviewDTO.builder()
                .reviewId(this.reviewId)
                .reviewContent(this.reviewContent)
                .reviewCrtDate(this.reviewCrtDate)
                .reviewUdtDate(this.reviewUdtDate)
                .reviewRating(this.reviewRating)
                .paymentId(this.payment.getPaymentId())
                .cartId(this.payment.getCartContents().getCart().getCartId())
                .contentsId(this.payment.getCartContents().getContents().getContentsId())
                .memberDTO(this.member.toDTO())
                .build();
    }
}
