package com.bit.envdev.entity;

import com.bit.envdev.dto.TagDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tagId;

    @Column
    private String tagContent;

    public TagDTO toDTO() {
        return TagDTO.builder()
                .tagId(this.tagId)
                .tagContent(this.tagContent)
                .build();
    }

}
