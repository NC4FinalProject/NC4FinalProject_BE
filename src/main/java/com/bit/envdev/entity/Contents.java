package com.bit.envdev.entity;

import com.bit.envdev.dto.ContentsDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

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
            generator = "ContentsSeqGenerator"
    )
    private int contentsId;
    
    @Column(nullable=true, length=100)
    private String contentsTitle;

    @OneToMany(mappedBy = "contents", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Section> sectionList;

    @OneToMany(mappedBy = "contents", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Video> videoList;

    // @JsonIgnoreProperties
    // @ManyToOne(fetch = FetchType.LAZY)

    @ManyToOne(fetch = FetchType.LAZY) // 한명의 유저는 여러개의 게시글을 갖을 수 있다, 여러개의 게시글의 유저는 한명이다.
    @JoinColumn(name="member_id") // JPA(ORM)을 사용하여 오브젝트 자체를 저장 할 수 있고 이를 Foreign Key로 사용 가능
    @JsonBackReference
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

    @Column(nullable = true)
    private int price;

    @Column(nullable = true)
    private String priceType;

    @Column
    private String thumbnail;
    
    // private LocalDateTime mokDate;
    // private Timestamp mokDate;

    @CreationTimestamp
    private LocalDateTime regDate;

    // @LastModifiedDate
    @UpdateTimestamp
    private LocalDateTime modDate;

    @OneToMany(mappedBy = "contents", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ContentsFile> contentsFileList;

    @Column(nullable = true)
    private double reviewRating;

    @Column(nullable = true)
    private int reviewCount;

    @Column(nullable = true)
    private int paymentCount;

    private int bookmarkCount;

    // // 이넘 데이터 관련
    // @Enumerated(EnumType.STRING)
	// private RoleType role; // USER, ADMIN // or // private ItemSellStatus itemSellSTatus;

    // // 데이터 관련 CLOB, BLOB
    // @Lob
    // private int stockNumber;

    public ContentsDTO toDTO() {
        return ContentsDTO.builder()
                .memberId(this.member.getUsername())
                .userNickname(this.member.getUserNickname())
                .contentsId(this.contentsId)
                .contentsTitle(this.contentsTitle)
                .category(this.category)
                .price(this.price)
                .thumbnail(this.thumbnail)
                .regDate(this.regDate)
                .modDate(this.modDate)
                .introduce(this.introduce)
                .sectionList(this.sectionList != null ? this.sectionList.stream().map(Section::toDTO).toList() : null)
                .videoList(this.videoList != null ? this.videoList.stream().map(Video::toDTO).toList() : null)
                .contentsFileDTOList(this.contentsFileList != null ? this.contentsFileList.stream().map(ContentsFile::toDTO).toList() : null)
                .reviewRating(this.reviewRating)
                .reviewCount(this.reviewCount)
                .paymentCount(this.paymentCount)
                .bookmarkCount(this.bookmarkCount)
                .build();
    }

    public void addVideoFile(Video video) {
        this.videoList.add(video);
    }
}