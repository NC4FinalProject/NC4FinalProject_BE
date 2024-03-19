package com.bit.envdev.entity;

import com.bit.envdev.dto.BoardDTO;
import com.bit.envdev.dto.BoardDTO.BoardDTOBuilder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Board {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "username")
    private Member member;

    @Column
    private String boardCategory;

    @Column
    private String boardTitle;

    @Column(length = 500)
    private String boardContents;

    @Column
    private boolean checked;

    public BoardDTO toDTO() {
        return BoardDTO.builder()
                .id(this.id)
                .username(this.member.getUsername())
                .boardCategory(this.boardCategory)
                .boardTitle(this.boardTitle)
                .boardContents(this.boardContents)
                .checked(this.checked)
                .build();
    }
}
