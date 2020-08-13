package com.cartrapido.main.service;

import com.cartrapido.main.domain.entity.Cart;
import com.cartrapido.main.domain.entity.Product;
import com.cartrapido.main.domain.repository.CartRepository;
import com.cartrapido.main.web.dto.CartDTO;
import com.cartrapido.main.web.dto.ProductDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CartService {
    CartRepository cartRepository;

    @Transactional
    public Cart saveCart(CartDTO cartDTO) {
        System.out.print(cartDTO.getUserEmail() + "의 카트에 넣음");
        Cart cart = cartRepository.save(cartDTO.toEntity());
        return cart;
    }

    @Transactional
    public boolean checkCart(Long productId, String userEmail) {
        System.out.print("중복 상품 있는지 확인");
        Cart cart = cartRepository.findAllByProductIdAndUserEmail(productId, userEmail);

        if (cart==null) {
            return true;
        } else return false;
    }

    @Transactional
    public void updateCart(Long productId, String userEmail){
        Cart cart = cartRepository.findAllByProductIdAndUserEmail(productId, userEmail);

//        List<CartDTO> cartDTOList = new ArrayList<>();
    }

    @Transactional
    public List<CartDTO> getCartList(String userEmail) {
        List<Cart> cartslist = cartRepository.findAllByUserEmailOrderByStore(userEmail);
        List<CartDTO> cartDTOList = new ArrayList<>();

        for (Cart cart : cartslist) {
            CartDTO cartDTO = CartDTO.builder()
                    .cartId(cart.getCartId())
                    .userEmail(cart.getUserEmail())
                    .productId(cart.getProductId())
                    .amount(cart.getAmount())
                    .productName(cart.getProductName())
                    .productPrice(cart.getProductPrice())
                    .image(cart.getImage())
                    .store(cart.getStore())
                    .build();

            cartDTOList.add(cartDTO);


        }

        return cartDTOList;
    }
}
