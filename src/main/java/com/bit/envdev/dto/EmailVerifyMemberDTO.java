package com.bit.envdev.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmailVerifyMemberDTO {
    private String username;
    private String password;
    private String userNickname;
    private String emailVerification;
}
