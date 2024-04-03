package com.bit.envdev.dto;

import com.bit.envdev.entity.Contents;
import com.bit.envdev.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ContentsDTO {

    private String username;

    private int contentsId;
    
    private String contentsTitle;

    private String category;

    private String introduce;

    private String price;

    private String priceType;

    private List<SectionDTO> sectionDTOList;

    public Contents toEntity(Member member) {
        return Contents.builder()
                .contentsId(this.contentsId)
                .contentsTitle(this.contentsTitle)
                .member(member)
                .category(this.category)
                .introduce(this.introduce)
                .price(this.price)
                .build();
    }

}