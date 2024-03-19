package com.bit.envdev.entity;

import com.bit.envdev.dto.PointDTO;
import jakarta.persistence.Entity;
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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "point")
public class Point {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pointId;

    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "username", insertable=false, updatable=false)
    private Member member;

    private int totalPoint;

    private LocalDate pointModifiedDate;

    private String username;
    public PointDTO toDTO() {
        return PointDTO.builder()
                .username(member.getUsername())
                .totalPoint(totalPoint)
                .pointModifiedDate(pointModifiedDate)
                .build();
    }
}
