package com.bit.envdev.entity;

import com.bit.envdev.dto.CartContentsDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@IdClass(CartContentsId.class)
public class CartContents {
    @Id
    @JoinColumn(name = "cart_id")
    @ManyToOne
    private Cart cart;

    @Id
    @JoinColumn(name = "contents_id")
    @ManyToOne
    private Contents contents;

    @Column(columnDefinition = "boolean default false")
    private boolean isPaid;

    public CartContentsDTO toDTO() {
        return CartContentsDTO.builder()
                .cartId(this.cart.getCartId())
                .contentsId(this.contents.getContentsId())
                .isPaid(this.isPaid)
                .build();
    }
}
