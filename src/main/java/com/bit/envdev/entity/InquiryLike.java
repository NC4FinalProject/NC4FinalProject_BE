package com.bit.envdev.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class InquiryLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long inquiryLikeId;

    @ManyToOne
    @JoinColumn(name="review_id")
    private Inquiry inquiry;

    @ManyToOne
    @JoinColumn(name="id", referencedColumnName = "id")
    private Member member;
}
