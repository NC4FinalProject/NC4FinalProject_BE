package com.bit.envdev.entity;

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

}
