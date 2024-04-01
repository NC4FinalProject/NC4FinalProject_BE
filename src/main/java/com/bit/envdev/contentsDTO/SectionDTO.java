package com.bit.envdev.contentsDTO;

import java.util.List;


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
    
    private String sectionId;

    private String sectionTtitle;

    private List<SectionSubDTO> sectionSubList;

    
}
