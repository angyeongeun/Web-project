package com.cartrapido.main.domain.repository;

import com.cartrapido.main.domain.entity.Cart;
import com.cartrapido.main.domain.entity.Product;
import com.cartrapido.main.web.dto.CartDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository  extends JpaRepository<Cart, Long> {
    //void save( String userEmail , Long productId , int amount );
    Cart findAllByProductIdAndUserEmail(Long productId, String userEmail);
    List<Cart> findAllByUserEmail(String userEmail);
    List<Cart> findAllByUserEmailOrderByStore(String userEmail);

}
