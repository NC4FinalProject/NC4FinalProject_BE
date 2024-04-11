package com.bit.envdev.entity;


import com.bit.envdev.dto.VideoDTO;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


//@JsonBackReference
//@JsonManagedReference

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
    private String videoTime;

    @Column
    private String videoStorageId;

    @Column
    private String videoPath;


    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VideoReply> videoReplyList;

    public VideoDTO toDTO() {
        return VideoDTO.builder()
                .contentsId(this.contents.getContentsId())
                .videoId(this.videoId)
                .videoTitle(this.videoTitle)
                .videoTime(this.videoTime)
                .videoStorageId(this.videoStorageId)
                .videoPath(this.videoPath)
                .videoReplyList(this.videoReplyList != null ? this.videoReplyList.stream().map(VideoReply::toDTO).toList() : null) // 변환된 리스트 추가
                .build();
    }


    public void addVideoReplyList(VideoReply videoReply) {
        this.videoReplyList.add(videoReply);
    }

}
