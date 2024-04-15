package com.bit.envdev.dto;

import com.bit.envdev.entity.Cart;
import com.bit.envdev.entity.Member;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CartDTO {
    private long cartId;
    private long memberId;
    private List<CartContentsDTO> cartContentsDTOList;
    private boolean isPaid;
    private int contentsId;

    public Cart toEntity() {
        return Cart.builder()
                .cartId(this.cartId)
                .member(Member.builder().memberId(this.memberId).build())
                .cartContentsList(new ArrayList<>())
                .isPaid(this.isPaid)
                .build();
    }
}
