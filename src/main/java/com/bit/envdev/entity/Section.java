package com.bit.envdev.entity;

import com.bit.envdev.dto.SectionDTO;
import com.bit.envdev.dto.SectionSubDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@IdClass(SectionId.class)
public class Section {

    @Id
    @ManyToOne(fetch = FetchType.LAZY) // 한명의 유저는 여러개의 게시글을 갖을 수 있다, 여러개의 게시글의 유저는 한명이다.
    @JoinColumn(name="contents_id")
    private Contents contents;

    @Id
    private int sectionId;

    @Column
    private String sectionTitle;

    @OneToMany(mappedBy = "section", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SectionSub> sectionSubList;

    public SectionDTO toDTO() {
        return SectionDTO.builder()
                .contentsId(this.contents.getContentsId())
                .sectionId(this.sectionId)
                .sectionTitle(this.sectionTitle)
                .sectionSubList(this.sectionSubList != null ? this.sectionSubList.stream().map(SectionSub::toDTO).toList() : null) // 변환된 리스트 추가
                .build();
    }


    public void addSectionSubList(SectionSub sectionSub) {
        this.sectionSubList.add(sectionSub);
    }
}