package com.bit.envdev.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CartContentsId implements Serializable {
    @EqualsAndHashCode.Include
    private long cart;
    @EqualsAndHashCode.Include
    private long contents;
}
