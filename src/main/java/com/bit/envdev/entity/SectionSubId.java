package com.bit.envdev.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class SectionSubId implements Serializable {
    private int sectionSubId;
    private SectionId section;
}
