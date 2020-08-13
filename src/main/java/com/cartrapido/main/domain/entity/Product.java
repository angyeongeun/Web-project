package com.cartrapido.main.domain.entity;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(length = 100, nullable = false)
    private String store;

    @Column(length = 100, nullable = false)
    private String category;

    @Column(length = 100, nullable = false)
    private String productName;

    @Column(length = 100, nullable = false)
    private int productPrice;

    @Column(length = 100, nullable = false)
    private int productQty;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String productContent;

    @Column
    private String image;

    @Builder
    public Product(Long productId, String productName, String productContent,
                   int productPrice, int productQty, String store,
                   String category, String image) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productQty = productQty;
        this.productContent = productContent;
        this.store = store;
        this.category = category;
        this.image = image;
    }


}
