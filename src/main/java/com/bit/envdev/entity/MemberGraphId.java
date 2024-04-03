package com.bit.envdev.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class MemberGraphId implements Serializable {
    private LocalDateTime registration_date;
    private Long user_count;
}
