package com.cartrapido.main.web.dto;

import com.cartrapido.main.domain.entity.Cart;
import lombok.Builder;
import lombok.Getter;


@Getter
public class CartDTO {
    private Long cartId;
    private String userEmail;
    private Long productId;
    private int amount;
    private String productName;
    private int productPrice;
    private String image;
    private String store;




    public Cart toEntity(){
        Cart cart = Cart.builder()
                .userEmail(userEmail)
                .productId(productId)
                .amount(amount)
                .productName(productName)
                .productPrice(productPrice)
                .image(image)
                .store(store)
                .build();

        return cart;
    }

    @Builder
    public CartDTO(Long cartId , String userEmail , Long productId ,
                   int amount, String productName , int productPrice, String image,String store){
        this.cartId  = cartId;
        this.userEmail = userEmail;
        this.productId = productId;
        this.amount = amount;
        this.productName = productName;
        this.productPrice = productPrice;
        this.image = image;
        this.store = store;
    }

    @Builder
    public CartDTO(String userEmail , Long productId , int amount, String productName,
                   int productPrice, String image,String store){
        this.userEmail = userEmail;
        this.productId = productId;
        this.amount = amount;
        this.productName = productName;
        this.productPrice = productPrice;
        this.image = image;
        this.store = store;
    }

}
