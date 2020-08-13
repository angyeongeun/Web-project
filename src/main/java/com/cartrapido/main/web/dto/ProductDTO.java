package com.cartrapido.main.web.dto;

import com.cartrapido.main.domain.entity.Product;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ProductDTO {


    private Long productId;
    private String productName;
    private int productPrice;
    private int productQty;
    private String productContent;
    private String store;
    private String category;
    private String image;

    public Product toEntity(){
        Product product = Product.builder()
                .productId(productId)
                .productName(productName)
                .productPrice(productPrice)
                .productQty(productQty)
                .productContent(productContent)
                .store(store)
                .category(category)
                .image(image)
                .build();
        return product;
    }

    @Builder
    public ProductDTO(Long productId, String productName, String productContent,
                      int productPrice, int productQty, String store,
                      String category, String image){
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
