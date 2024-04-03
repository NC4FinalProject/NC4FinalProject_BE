package com.bit.envdev.contentsDTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InsertRequestDTO {
    private ContentsDTO contentsDTO;
    private List<SectionDTO> sectionDTO;

}
