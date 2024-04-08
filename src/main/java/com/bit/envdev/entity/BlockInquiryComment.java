package com.bit.envdev.entity;

import com.bit.envdev.constant.BlockState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlockInquiryComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    @OneToOne
    private InquiryComment comment;

    @Column(updatable = false, nullable = false)
    @Builder.Default
    private LocalDateTime blockDate = LocalDateTime.now();

    @Column(updatable = false, nullable = false)
    @Builder.Default
    private LocalDateTime blockPeriod = LocalDateTime.now();

    @Column
    @Convert(converter = BlockState.class)
    @Builder.Default
    private BlockState state = BlockState.BLOCKING;
}
