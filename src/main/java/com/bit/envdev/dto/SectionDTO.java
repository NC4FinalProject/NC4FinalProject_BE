package com.bit.envdev.dto;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


import com.bit.envdev.entity.Contents;
import com.bit.envdev.entity.Member;
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
public class SectionDTO {

    private int contentsId;
    
    private int sectionId;

    private String sectionTitle;

    private List<SectionSubDTO> sectionSubList;

    public Section toEntity(Contents contents) {
        return Section.builder()
                .contents(contents)
                .sectionId(this.sectionId)
                .sectionTitle(this.sectionTitle)
                .sectionSubList(new ArrayList<>())
                .build();
    }
}
