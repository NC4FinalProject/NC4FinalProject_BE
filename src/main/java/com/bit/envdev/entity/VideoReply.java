package com.bit.envdev.entity;

import com.bit.envdev.dto.SectionSubDTO;
import com.bit.envdev.dto.VideoReplyDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@IdClass(VideoReplyId.class)
public class VideoReply {

    @Id
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "contents_id"),
            @JoinColumn(name = "video_id")
    })
    private Video video;

    @Id
    private int videoReplyId;

    @ManyToOne(fetch = FetchType.EAGER) // 한명의 유저는 여러개의 게시글을 갖을 수 있다, 여러개의 게시글의 유저는 한명이다.
    @JoinColumn(name="id") // JPA(ORM)을 사용하여 오브젝트 자체를 저장 할 수 있고 이를 Foreign Key로 사용 가능
    private Member member;

    @Column
    private String videoReplyContent;

    @CreationTimestamp
    private LocalDateTime regDate;

    // @LastModifiedDate
    @UpdateTimestamp
    private LocalDateTime modDate;

    public VideoReplyDTO toDTO() {
        return VideoReplyDTO.builder()
                .memberId(this.member.getUsername())
                .videoReplyId(this.videoReplyId)
                .videoReplyContent(this.videoReplyContent)
                .videoId(this.video.getVideoId())
                .build();
    }

}
