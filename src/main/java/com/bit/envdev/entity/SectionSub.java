package com.bit.envdev.entity;

import com.bit.envdev.dto.SectionDTO;
import com.bit.envdev.dto.SectionSubDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@IdClass(SectionSubId.class)
public class SectionSub {

    @Id
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "contents_id"),
            @JoinColumn(name = "section_id")
    })
    private Section section;

    @Id
    private int sectionSubId;

    @Column
    private String sectionSubTitle;

    public SectionSubDTO toDTO() {
        return SectionSubDTO.builder()
                .sectionSubId(this.sectionSubId)
                .sectionSubTitle(this.sectionSubTitle)
                .sectionId(this.section.getSectionId())
                .build();
    }
}
