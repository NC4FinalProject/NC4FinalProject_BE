package com.bit.envdev.dto;

import com.bit.envdev.entity.Inquiry;
import com.bit.envdev.entity.Tag;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TagDTO {

    private int tagId;
    private String tagContent;

    public Tag toEntity() {
        return Tag.builder()
                .tagId(this.tagId)
                .tagContent(this.tagContent)
                .build();
    }

}
