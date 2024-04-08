package com.bit.envdev.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
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

    @OneToOne
    @JoinColumn(name = "member_id", referencedColumnName = "member_id")
    private Member member;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<CartContents> cartContentsList;

    @Column(columnDefinition = "boolean default false")
    private boolean isPaid;

}
