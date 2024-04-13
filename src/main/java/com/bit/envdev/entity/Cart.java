package com.bit.envdev.entity;

import com.bit.envdev.dto.CartDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.mapping.ToOne;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SequenceGenerator(
        name = "cartSeqGenerator",
        sequenceName = "T_CART_SEQ",
        initialValue = 1,
        allocationSize = 1
)
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "cartSeqGenerator")
    private long cartId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<CartContents> cartContentsList;

    @Column(columnDefinition = "boolean default false")
    private boolean isPaid;

    public CartDTO toDTO() {
        return CartDTO.builder()
                .cartId(this.cartId)
                .memberId(this.member.getMemberId())
                .cartContentsDTOList(this.cartContentsList.stream()
                        .map(CartContents::toDTO)
                        .toList())
                .isPaid(this.isPaid)
                .build();
    }

    public void addCartContentsList(CartContents cartContents) {
        this.cartContentsList.add(cartContents);
    }

}
