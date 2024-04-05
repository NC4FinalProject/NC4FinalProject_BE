package com.bit.envdev.entity;

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

    @Column
    private String replyContent;

    @CreationTimestamp
    private LocalDateTime regDate;

    // @LastModifiedDate
    @UpdateTimestamp
    private LocalDateTime modDate;

}
