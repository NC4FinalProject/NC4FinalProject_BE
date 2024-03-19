package com.bit.envdev.entity;

import com.bit.envdev.dto.PointHistoryDTO;
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
    @Table(name = "pointHistory")
    public class PointHistory {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long pointHistoryId;

        @ManyToOne
        @JoinColumn(name = "username", referencedColumnName = "username", insertable=false, updatable=false)
        private Member member;

        private int Point;

        private String pointCategory;

        private LocalDate pointHistoryModifiedDate;

        private String username;

        private String orderId;

        public PointHistoryDTO toDTO() {
            return PointHistoryDTO.builder()
                    .username(member.getUsername())
                    .orderId(orderId)
                    .Point(Point)
                    .pointCategory(pointCategory)
                    .pointHistoryModifiedDate(pointHistoryModifiedDate)
                    .build();
        }
    }

