package com.bit.envdev.entity;

import com.bit.envdev.dto.ReviewDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.mapping.ToOne;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long reviewId;
    @ManyToOne
    @JoinColumn(name="id", referencedColumnName = "id")
    private Member member;
    @Column(nullable = false)
    private String reviewContent;
    @Column(nullable = false)
    private String reviewRegdate;
    @Column(nullable = false)
    private String reviewModidate;
    @Column(nullable = false)
    private String reviewRating;

    public ReviewDTO toDTO() {
        return ReviewDTO.builder()
                .reviewId(this.reviewId)
                .userNickname(this.member.getUserNickname())
                .reviewContent(this.reviewContent)
                .reviewRegdate(this.reviewRegdate)
                .reviewModidate(this.reviewModidate)
                .reviewRating(this.reviewRating)
                .build();
    }

}
