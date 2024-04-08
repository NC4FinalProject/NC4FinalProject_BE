package com.bit.envdev.entity;

import com.bit.envdev.dto.PointDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@EntityListeners(AuditingEntityListener.class)
@Table(name = "point")
public class Point {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pointId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    @JsonBackReference
    private Member member;

    private int value;
    private String reason;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDate createdAt;

    public PointDTO toDTO() {
        return PointDTO.builder()
                .member(this.member)
                .value(this.value)
                .reason(this.reason)
                .createdAt(this.createdAt.toString())
                .build();
    }
}
