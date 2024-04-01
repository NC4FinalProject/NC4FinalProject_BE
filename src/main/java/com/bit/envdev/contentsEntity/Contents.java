package com.bit.envdev.contentsEntity;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.joda.time.DateTime;
import org.springframework.data.annotation.LastModifiedDate;

import com.bit.envdev.contentsDTO.ContentsDTO;
import com.bit.envdev.entity.Member;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
    
    @Column(nullable=true, length=100, unique = true)
    private String contentsTitle;

    // @JsonIgnoreProperties
    // @ManyToOne(fetch = FetchType.LAZY)
    @ManyToOne(fetch = FetchType.EAGER) // 한명의 유저는 여러개의 게시글을 갖을 수 있다, 여러개의 게시글의 유저는 한명이다.
    @JoinColumn(name="id") // JPA(ORM)을 사용하여 오브젝트 자체를 저장 할 수 있고 이를 Foreign Key로 사용 가능
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
                .build();
    }

}