package com.bit.envdev.entity;

import org.hibernate.annotations.ColumnDefault;
import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.bit.envdev.constant.Role;
import com.bit.envdev.dto.MemberDTO;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true)
    private String userNickname;

    @Enumerated(EnumType.STRING)
    @Column
    private Role role;

    @Column
    private String profileFile;

    @ColumnDefault("false")
    private boolean wannabeTeacher;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime modifiedAt;

    @PrePersist // 엔티티가 저장되기 전에 실행될 메서드
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.modifiedAt = LocalDateTime.now();
    }

    @PreUpdate // 엔티티가 업데이트되기 전에 실행될 메서드
    public void preUpdate() {
        this.modifiedAt = LocalDateTime.now();
    }

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Contents> contentsList;

    public MemberDTO toDTO() {
        return MemberDTO.builder()
                .id(this.id)
                .username(this.username)
                .password(this.password)
                .userNickname(this.userNickname)
                .role(this.role)
                .profileFile(this.profileFile)
                .wannabeTeacher(this.wannabeTeacher)
                .createdAt(this.createdAt.toString())
                .modifiedAt(this.modifiedAt.toString())
                .build();
    }
}
