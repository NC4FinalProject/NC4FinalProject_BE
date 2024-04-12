package com.bit.envdev.entity;


import com.bit.envdev.dto.ContentsBookmarkDTO;
import jakarta.persistence.*;
import lombok.*;



@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "contents_bookmark")
@AllArgsConstructor
@Builder
@IdClass(ContentsBookmarkId.class)
public class ContentsBookmark {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contents_id")
    private Contents contents;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public ContentsBookmarkDTO toDTO() {
        return ContentsBookmarkDTO.builder()
                .contentsId(this.contents.getContentsId())
                .memberId(this.member.getMemberId())
                .build();
    }
}
