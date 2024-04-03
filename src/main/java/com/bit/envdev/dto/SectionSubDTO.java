package com.bit.envdev.dto;

import com.bit.envdev.entity.Section;
import com.bit.envdev.entity.SectionSub;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SectionSubDTO {

    private int sectionId;

    private int sectionSubId;

    private String sectionSubTitle;

    public SectionSub toEntity(Section section) {
        return SectionSub.builder()
                .section(section)
                .sectionSubId(this.sectionSubId)
                .sectionSubTitle(this.sectionSubTitle)
                .build();
    }
}
