package com.bit.envdev.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter

public class PaymentId implements Serializable {
    @EqualsAndHashCode.Include
    private long paymentId;
    @EqualsAndHashCode.Include
    private CartContentsId cartContents;
}
