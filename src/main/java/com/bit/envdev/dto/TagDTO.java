package com.bit.envdev.dto;

import com.bit.envdev.entity.Inquiry;
import com.bit.envdev.entity.Tag;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TagDTO {

    private long inquiryId;
    private int tagId;
    private String tagContent;

    public Tag toEntity(Inquiry inquiry) {
        return Tag.builder()
                .tagId(this.tagId)
                .tagContent(this.tagContent)
                .inquiry(inquiry)
                .build();
    }

}
