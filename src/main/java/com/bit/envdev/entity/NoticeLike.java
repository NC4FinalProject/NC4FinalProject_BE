package com.bit.envdev.entity;

import com.bit.envdev.dto.NoticeLikeDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "notice_like")
@AllArgsConstructor
@Builder
@IdClass(NoticeLikeId.class)
public class NoticeLike {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notice_id")
    private Notice notice;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public NoticeLikeDTO toDTO() {
        return NoticeLikeDTO.builder()
                .noticeId(this.notice.getId())
                .member(this.member.getMemberId())
                .build();
    }
}

