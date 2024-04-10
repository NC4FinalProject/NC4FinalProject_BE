package com.bit.envdev.dto;

import com.bit.envdev.entity.Cart;
import com.bit.envdev.entity.CartContents;
import com.bit.envdev.entity.Contents;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartContentsDTO {
    private long cartId;
    private int contentsId;
    private boolean isPaid;

    public CartContents toEntity() {
        return CartContents.builder()
                .cart(Cart.builder()
                        .cartId(this.cartId)
                        .build())
                .contents(Contents.builder()
                        .contentsId(this.contentsId)
                        .build())
                .build();
    }
}
