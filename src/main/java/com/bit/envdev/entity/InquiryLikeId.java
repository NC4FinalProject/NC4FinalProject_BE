package com.bit.envdev.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class InquiryLikeId implements Serializable {
    private long inquiry;
    private long member;
}
