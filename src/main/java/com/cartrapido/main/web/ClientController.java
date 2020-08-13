package com.cartrapido.main.web;

import com.cartrapido.main.domain.entity.Cart;
import com.cartrapido.main.domain.entity.Product;
import com.cartrapido.main.service.CartService;
import com.cartrapido.main.service.ProductService;
import com.cartrapido.main.web.dto.CartDTO;
import com.cartrapido.main.web.dto.ProductDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
@AllArgsConstructor
public class ClientController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CartService cartService;

/*    @GetMapping("/clientMain")
    public String clientWeb() {

        return "/clientWebBody/clientMain";
    }*/


//    //마트들 상품 보여줌
//    @GetMapping("/clientMart")
//    public String clientMart(Model model) {
//        List<ProductDTO> productList = productService.getProductList();
//        model.addAttribute("productList",productList);
//
//        return "/clientWebBody/mart";
//    }


//    //마트별로(where store) 상품 보여줌 //
//    @GetMapping("/clientMart/{mart}")
//    public String detail( @PathVariable("mart") String mart, Model model) {
////        List<ProductDTO> productList = productService.getProductList();
//        System.out.println(mart);
//        List<ProductDTO> productList = productService.getStoreProductList(mart);
//        model.addAttribute("productList",productList);
//        model.addAttribute("mart",mart);
//
//        return "/clientWebBody/mart";
//    }

    //페이징 적용
    //마트별로(where store) 상품 보여줌 //
    @GetMapping("/clientMart/mart")
    public String mart(Model model,
                       @PageableDefault(size=3, sort = "productId", direction = Sort.Direction.DESC) Pageable pageable) {
        List<ProductDTO> productList = productService.getProductList(pageable);
        Page<Product> products = productService.productListPage(pageable);
        int startPage = Math.max(1, products.getPageable().getPageNumber()-5);
        int endPage = Math.min(products.getTotalPages(), products.getPageable().getPageNumber()+5);
        model.addAttribute("productList",productList);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        model.addAttribute("mart","homplus");
        model.addAttribute("products",products);

        return "/clientWebBody/mart";
    }

    @PostMapping("/clientMart/putInCart")
    @ResponseBody
    public void putInCart(@RequestParam Long productId, HttpSession session,
                          @RequestParam(required=true,defaultValue="1") Integer amount) {

        ProductDTO productDTO = productService.getProductInfo(productId);
        String userEmail = (String) session.getAttribute("userEmail");
        System.out.println(productDTO.getStore()+" 상품을 카트에 넣는다");
        CartDTO cartDTO = new CartDTO(userEmail,productId,amount,
                productDTO.getProductName(), productDTO.getProductPrice(),
                productDTO.getImage(), productDTO.getStore());

        if(cartService.checkCart(productId, userEmail)==true) cartService.saveCart(cartDTO);
    }

    @PostMapping("/clientWeb/pushOrder")
    @ResponseBody /*@ModelAttribute List<CartDTO> cartList*/
    public void pushOrder(@RequestParam String test) {
        System.out.println("주문서를 DB에 저장");
/*        for(CartDTO dto : cartList){
            System.out.println(dto.getProductName()+"을 " +dto.getAmount()+" 개 주문");
        }*/

    }



    @GetMapping("/clientLayout")
    public String clientLayout() {

        return "/clientWeb/clientLayout";
    }

    @GetMapping("/clientMypage")
    public String clientMypage() {

        return "/clientWebBody/myPage";
    }

    @GetMapping("/shoppingCart")
    public String shoppingCart(HttpSession session, Model model) {
        String userEmail = (String) session.getAttribute("userEmail");

        List<CartDTO> cartList = cartService.getCartList(userEmail);

        for(CartDTO cartDTO :cartList) {
            System.out.println("카트 아이디 " + cartDTO.getCartId());
            System.out.println("카트 주인 " + cartDTO.getUserEmail());
            System.out.println("들어갈 상품 번호 " + cartDTO.getProductId());
            System.out.println("들어갈 상품 번호 " + cartDTO.getImage());
        }

        model.addAttribute("cartList", cartList);
        ;
        return "/clientWebBody/shoppingCart";
    }

    @GetMapping("/clientChatting")
    public String clientChatting() {

        return "/clientWebBody/clientChatting.html";
    }










}
