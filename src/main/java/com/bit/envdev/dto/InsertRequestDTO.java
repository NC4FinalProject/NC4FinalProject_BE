package com.bit.envdev.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class InsertRequestDTO {
    private ContentsDTO contentsDTO;
    private List<VideoDTO> videoDTO;
    private List<SectionDTO> sectionDTO;
}
