package com.bit.envdev.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor

public class Inquiry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long inquiryId;
    @Column(nullable = false)
    private String inquiryTitle;
    @Column(nullable = false)
    private String inquiryContent;

    @Column
    @Temporal(TemporalType.DATE)
    @CreationTimestamp
    private Date inquiryCommentCrtDT;

    @Column
    @Temporal(TemporalType.DATE)
    @UpdateTimestamp
    private Date inquiryCommentUdtDT;

    @Column(columnDefinition = "boolean default false")
    private boolean isPrivate;

    @Column(columnDefinition = "boolean default false")
    private boolean isSolved;

    @OneToMany(mappedBy = "inquiry", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Tag> tagMappingList;

    @OneToMany(mappedBy = "inquiry", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<InquiryFile> inquiryFileList;
}
