package com.bit.envdev.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@IdClass(CartLectureId.class)
public class CartLecture {
    @Id
    @JoinColumn(name = "cart_id")
    @ManyToOne
    @JsonBackReference
    private Cart cart;

    @Id
    @JoinColumn(name = "lecture_id")
    @ManyToOne
    private Lecture lecture;

    @OneToOne(mappedBy = "cartLecture", cascade = CascadeType.ALL)
    private Payment payment;

    @Column(columnDefinition = "boolean default false")
    private boolean isPaid;

}
