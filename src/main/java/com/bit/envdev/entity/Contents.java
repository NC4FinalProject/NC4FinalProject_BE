package com.bit.envdev.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.joda.time.DateTime;
import org.springframework.data.annotation.LastModifiedDate;

import com.bit.envdev.dto.ContentsDTO;
import com.bit.envdev.entity.Member;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
@SequenceGenerator(
        name = "ContentsSeqGenerator",
        sequenceName = "T_Contents_SEQ",
        initialValue = 1,
        allocationSize = 1
)
public class Contents {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "ContnetsSeqGenerator"
    )
    private int contentsId;
    
    @Column(nullable=true, length=100)
    private String contentsTitle;

    @OneToMany(mappedBy = "contents", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Section> sectionList;

    // @JsonIgnoreProperties
    // @ManyToOne(fetch = FetchType.LAZY)
    @ManyToOne(fetch = FetchType.EAGER) // 한명의 유저는 여러개의 게시글을 갖을 수 있다, 여러개의 게시글의 유저는 한명이다.
    @JoinColumn(name="member_id") // JPA(ORM)을 사용하여 오브젝트 자체를 저장 할 수 있고 이를 Foreign Key로 사용 가능
    private Member member;

    // //JoinColumn(name="replyId")  // 하나의 컬럼은 원자성을 갖음으로 조인컬럼을 통한 참조키 불가능
	// @OneToMany(mappedBy = "board", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	// @JsonIgnoreProperties({"board"})
	// @OrderBy("id desc")
	// private List<Reply> replys;

    // @ColumnDefault("")

    @Column(nullable=false)
    private String category;

    @Column(nullable=true)
    private String introduce;

    @Column(nullable=true)
    private String price;
    
    // private LocalDateTime mokDate;
    // private Timestamp mokDate;

    @CreationTimestamp
    private LocalDateTime regDate;

    // @LastModifiedDate
    @UpdateTimestamp
    private LocalDateTime modDate;

    // // 이넘 데이터 관련
    // @Enumerated(EnumType.STRING)
	// private RoleType role; // USER, ADMIN // or // private ItemSellStatus itemSellSTatus;

    // // 데이터 관련 CLOB, BLOB
    // @Lob
    // private int stockNumber;
    

    public ContentsDTO toDTO() {
        return ContentsDTO.builder()
                .username(this.member.getUsername())
                .contentsId(this.contentsId)
                .contentsTitle(this.contentsTitle)
                .category(this.category)
                .price(this.price)
                .sectionDTOList(this.sectionList != null ? this.sectionList.stream().map(Section::toDTO).toList() : null)
                .build();
    }

}