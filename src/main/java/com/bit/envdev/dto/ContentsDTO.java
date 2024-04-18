package com.bit.envdev.dto;

import com.bit.envdev.entity.Contents;
import com.bit.envdev.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ContentsDTO {

    private String memberId;

    private int contentsId;
    
    private String contentsTitle;

    private String category;

    private String introduce;

    private int price;

    private String priceType;

    private String thumbnail;

    private LocalDateTime regDate;

    private LocalDateTime modDate;

    private List<SectionDTO> sectionList;
    private List<VideoDTO> videoList;

    private String userNickname;
    private List<ContentsFileDTO> contentsFileDTOList;
    private double reviewRating;
    private int reviewCount;
    private int paymentCount;
    private int bookmarkCount;
    private String profileFile;

    public Contents toEntity(Member member) {
        return Contents.builder()
                .contentsId(this.contentsId)
                .contentsTitle(this.contentsTitle)
                .member(member)
                .category(this.category)
                .thumbnail(this.thumbnail)
                .introduce(this.introduce)
                .regDate(this.regDate)
                .modDate(this.modDate)
                .introduce(this.introduce)
                .videoList(new ArrayList<>())
                .sectionList(new ArrayList<>())
                .price(this.price)
                .contentsFileList(new ArrayList<>())
                .build();
    }

}