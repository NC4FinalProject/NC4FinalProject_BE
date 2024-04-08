package com.bit.envdev.dto;

import com.bit.envdev.constant.Role;
import com.bit.envdev.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class MemberDTO {
    private long memberId;
    private String username;
    private String password;
    private String userNickname;

    @Enumerated(EnumType.STRING)
    private Role role;
    private String token;
    private String profileFile;
    private boolean emailVerification;
    private String createdAt;
    private String modifiedAt;
    private String memo;


    public Member toEntity() {
        return Member.builder()
                .memberId(this.memberId)
                .username(this.username)
                .password(this.password)
                .userNickname(this.userNickname)
                .role(this.role)
                .profileFile(this.profileFile)
                .emailVerification(this.emailVerification)
                .createdAt(LocalDateTime.parse(this.createdAt))
                .modifiedAt(LocalDateTime.parse(this.modifiedAt))
                .memo(this.memo)
                .build();
    }

}
