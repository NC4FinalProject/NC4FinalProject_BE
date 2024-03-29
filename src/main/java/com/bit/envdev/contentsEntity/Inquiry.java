package com.bit.envdev.contentsEntity;

import com.bit.envdev.contentsDTO.ContentsDTO;
import com.bit.envdev.contentsDTO.InquiryDTO;

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

@Entity
@Table(name = "Inquiry")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Inquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int inquiryId;

    @Column(nullable = true)
    private String title;

    @Column(nullable = true)
    private String content;

    @Column(nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="contentsId")
    private Contents contents;

    public InquiryDTO toDTO() {
        return InquiryDTO.builder()
                .inquiryId(this.inquiryId)
                .title(this.title)
                .content(this.content)
                .contentsId(this.contents.getContentsId())
                .build();
    }


}