package com.bit.envdev.entity;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor // 모든 필드를 포함한 생성자를 자동 생성
@EqualsAndHashCode
public class VideoId implements Serializable {
        private int contents;
        private int videoId;
}
