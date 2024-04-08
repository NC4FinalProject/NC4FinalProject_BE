package com.bit.envdev.entity;

import com.bit.envdev.constant.Role;
import com.bit.envdev.dto.MemberDTO;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;
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
    private long memberId;

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

    @Column
    private String emailVerification;

    @OrderBy("createdAt DESC")
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Point> pointList;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime modifiedAt;

    @Column(length = 350)
    private String memo;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Contents> contentsList;

    public MemberDTO toDTO() {
        return MemberDTO.builder()
                .memberId(this.memberId)
                .username(this.username)
                .password(this.password)
                .userNickname(this.userNickname)
                .role(this.role)
                .profileFile(this.profileFile)
                .emailVerification(this.emailVerification)
                .createdAt(this.createdAt.toString())
                .modifiedAt(this.modifiedAt.toString())
                .memo(this.memo)
                .pointDTOList(this.pointList != null ? this.pointList.stream().map(Point::toDTO).toList() : null)
                .build();
    }

}
