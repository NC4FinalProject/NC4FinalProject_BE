package com.bit.envdev.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Lecture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long lectureId;
    private String teacherName;
    private String title;
    private int price;

    @JoinColumn(name = "id", referencedColumnName = "Id")
    @ManyToOne
    @JsonBackReference
    private Member member;

    @OneToMany(mappedBy = "lecture", cascade = CascadeType.ALL)
    private List<CartLecture> cartLectureList;

}
