package com.bit.envdev.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class VideoReplyId implements Serializable {
    private int videoReplyId;
    private VideoId video;
}
