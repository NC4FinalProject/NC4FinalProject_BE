package com.bit.envdev.dto;

import com.bit.envdev.entity.Member;
import com.bit.envdev.entity.Board;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class BoardDTO {
    
    
    private Long id;
    private String username;
    private String boardCategory;
    private String boardTitle;
    private String boardContents;
    private boolean checked;

    public Board toEntity(Member member) {
        return Board.builder()
                .id(this.id)
                .member(member)
                .boardCategory(this.boardCategory)
                .boardTitle(this.boardTitle)
                .boardContents(this.boardContents)
                .checked(this.checked)
                .build();
    }
}
