package com.cartrapido.main.domain.repository;

import com.cartrapido.main.domain.entity.Member;
import com.cartrapido.main.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {


    List<Product> findAllByStore(String store);
    List<Product> findAllByCategory(String catrgory);
    Product findAllByProductId(Long productId);
    List<Product> findAllByStoreAndCategory(String store, String category);

    /*Optional<Member> findByEmail(String email);*/
    /*@Query("SELECT p FROM member p ORDER BY p.id DESC")
    List<Member> findAllMember();*/

}
