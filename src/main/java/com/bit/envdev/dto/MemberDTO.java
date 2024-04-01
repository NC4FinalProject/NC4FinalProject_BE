package com.bit.envdev.dto;

import org.joda.time.LocalDateTime;

import com.bit.envdev.entity.Member;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDTO {
    private long id;
    private String username;
    private String password;
    private String userNickname;
    private String role;
    private String token;
    private String profileFile;
    private boolean wannabeTeacher;
    private String createdAt;
    private String modifiedAt;


    public Member toEntity() {
        return Member.builder()
                .id(this.id)
                .username(this.username)
                .password(this.password)
                .userNickname(this.userNickname)
                .role(this.role)
                .profileFile(this.profileFile)
                .wannabeTeacher(this.wannabeTeacher)
                .createdAt(LocalDateTime.parse(this.createdAt))
                .modifiedAt(LocalDateTime.parse(this.modifiedAt))
                .build();
    }

}
