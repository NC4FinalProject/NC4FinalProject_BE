package com.bit.envdev.entity;

import com.bit.envdev.dto.VideoReplyDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@IdClass(VideoId.class)
public class Video {

    @Id
    @ManyToOne(fetch = FetchType.LAZY) // 한명의 유저는 여러개의 게시글을 갖을 수 있다, 여러개의 게시글의 유저는 한명이다.
    @JoinColumn(name="contents_id") // JPA(ORM)을 사용하여 오브젝트 자체를 저장 할 수 있고 이를 Foreign Key로 사용 가능
    private Contents contents;

    @Id
    private int videoId;

    @Column(nullable=true, length=100)
    private String videoTitle;

    @Column
    private String videoPath;

    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VideoReply> ReplyList;

}
