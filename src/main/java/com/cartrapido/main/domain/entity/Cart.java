package com.cartrapido.main.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.lang.ref.Reference;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    @Column(length = 100, nullable = false)
    private String userEmail;

    @Column(nullable = false)
    private Long productId;

    @Column(length = 100, nullable = false)
    private String store;

    @Column(length = 100, nullable = false)
    private int amount;

    @Column(length = 100)
    private String productName;

    @Column(length = 100)
    private int productPrice;

    @Column(length = 100)
    private String image;


    @Builder
    public Cart(String userEmail, Long productId, int amount , String productName , int productPrice, String image, String store) {
        this.userEmail = userEmail;
        this.productId = productId;
        this.amount = amount;
        this.productName = productName;
        this.productPrice = productPrice;
        this.image = image;
        this.store = store;
    }


}
