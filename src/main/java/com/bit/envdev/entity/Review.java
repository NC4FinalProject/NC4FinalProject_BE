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

    @ManyToOne

    @JoinColumn(name = "member_id")

    private Member member;

    @OneToOne
    @JoinColumn(name = "contents_id", referencedColumnName = "contentsId")
    private Contents contents;

    public ReviewDTO toDTO() {
        return ReviewDTO.builder()
                .reviewId(this.reviewId)
                .reviewContent(this.reviewContent)
                .reviewCrtDate(this.reviewCrtDate)
                .reviewUdtDate(this.reviewUdtDate)
                .reviewRating(this.reviewRating)
                .memberDTO(this.member.toDTO())
                .contentsId(this.contents.getContentsId())
                .build();
    }
}
