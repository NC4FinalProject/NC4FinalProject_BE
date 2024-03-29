package com.bit.envdev.contentsEntity;

import org.joda.time.DateTime;

import com.bit.envdev.contentsDTO.ContentsDTO;
import com.bit.envdev.entity.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Contents {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int contentsId;
    
    @Column(nullable=false, length=100)
    private String contentsTitle;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id")
    private Member member;

    // private String category;

    // private String introduce;

    // private String price;

    // private DateTime regDate;

    // private DateTime modDate;

    public ContentsDTO toDTO() {
        return ContentsDTO.builder()
                .contentsId(this.contentsId)
                .contentsTitle(this.contentsTitle)
                .username(this.member.getUsername())
                .build();
    }

}