package com.bit.envdev.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ContentsBookmarkId implements Serializable {
    private int contents;

    private long member;
}
