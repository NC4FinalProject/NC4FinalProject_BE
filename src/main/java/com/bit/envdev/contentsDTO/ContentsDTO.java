package com.bit.envdev.contentsDTO;

import org.joda.time.DateTime;

import com.bit.envdev.contentsEntity.Contents;
import com.bit.envdev.dto.MemberDTO;
import com.bit.envdev.entity.Member;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
public class ContentsDTO {

    private int contentsId;
    
    private String contentsTitle;

    // private String category;

    // private String introduce;

    // private String price;

    // private DateTime regDate;

    // private DateTime modDate;

    private String username;


    public Contents toEntity(Member member) {
        return Contents.builder()
                .contentsId(this.contentsId)
                .contentsTitle(this.contentsTitle)
                .member(member)
                .build();
    }

}