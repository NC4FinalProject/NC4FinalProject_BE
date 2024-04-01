package com.bit.envdev.contentsDTO;

import com.bit.envdev.contentsEntity.Contents;
import com.bit.envdev.contentsEntity.Inquiry;
import com.bit.envdev.entity.Member;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class InquiryDTO {

    private int inquiryId;

    private String title;

    private String content;

    private int contentsId;

    private Long id;

    public Inquiry toEntity(Contents contents) {
        return Inquiry.builder()
                .inquiryId(this.inquiryId)
                .title(this.title)
                .title(this.title)
                .contents(contents)
                .build();
    }

}