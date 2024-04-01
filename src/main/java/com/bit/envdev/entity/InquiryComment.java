package com.bit.envdev.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;


@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class InquiryComment  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long inquiryCommentId;

    @Column
    @Temporal(TemporalType.DATE)
    @CreationTimestamp
    private Date inquiryCommentCrtDT;

    @Column
    @Temporal(TemporalType.DATE)
    @UpdateTimestamp
    private Date inquiryCommentUdtDT;

    @Column(nullable = false)
    private String InquiryCommentContent;

    @ManyToOne
    @JoinColumn(name="id", referencedColumnName = "id")
    private Member member;
}
