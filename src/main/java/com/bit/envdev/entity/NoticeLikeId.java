package com.bit.envdev.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class NoticeLikeId implements Serializable {
    private Long notice;
    private long member;
}
