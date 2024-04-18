package com.bit.envdev.dto;

import com.bit.envdev.entity.Member;
import com.bit.envdev.entity.Qna;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QnaDTO {
    private Long id;
    private Member askUser;
    private String answerUser;
    private String category;
    private String content;
    private boolean answered;
    private Date createdAt;

    public Qna toEntity() {
        return Qna.builder()
                .id(this.id)
                .askUser(this.askUser)
                .answerUser(this.answerUser)
                .category(this.category)
                .content(this.content)
                .answered(this.answered)
                .createdAt(this.createdAt)
                .build();
    }
}