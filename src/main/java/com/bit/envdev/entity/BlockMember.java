package com.bit.envdev.entity;

import com.bit.envdev.constant.BlockState;
import com.bit.envdev.converter.BlockStateConverter;
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
public class BlockMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    private Long memberId;

    @Column(updatable = false, nullable = false)
    @Builder.Default
    private LocalDateTime blockDate = LocalDateTime.now();

    @Column(updatable = false, nullable = false)
    private LocalDateTime blockPeriod;

    @Column
    @Convert(converter = BlockStateConverter.class)
    @Builder.Default
    private BlockState state = BlockState.BLOCKING;
}
