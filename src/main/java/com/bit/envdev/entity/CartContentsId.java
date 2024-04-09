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
    private int contents;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartContentsId)) return false;
        CartContentsId that = (CartContentsId) o;
        return cart == that.cart && contents == that.contents;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (int) (cart ^ (cart >>> 32));
        result = 31 * result + (int) (contents ^ (contents >>> 32));
        return result;
    }
}
