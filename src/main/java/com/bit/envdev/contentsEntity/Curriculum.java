package com.bit.envdev.contentsEntity;

import java.util.ArrayList;
import java.util.List;

import org.junit.experimental.categories.Category;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Curriculum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sectionId;

    private String sectionTitle;

    // @ManyToOne(fetch = FetchType.LAZY) // 부모 카테고리에 대한 참조
    // @JoinColumn(name = "contentsId") // 외래키는 parent_id
    // private Contents parent;

    // @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true) // 자식 카테고리들
    // private List<Curriculum> children = new ArrayList<>();

    // 생성자, 게터, 세터 생략

    // 편의 메소드를 사용해 부모-자식 관계를 쉽게 설정
    // public void addChild(Category child) {
    //     children.add(child);
    //     child.setParent(this);
    // }

    // public void removeChild(Category child) {
    //     children.remove(child);
    //     child.setParent(null);
    // }

}