package com.cartrapido.main.web;

import com.cartrapido.main.config.auth.dto.SessionUser;
import com.cartrapido.main.domain.entity.Cart;
import com.cartrapido.main.domain.entity.OrderNum;
import com.cartrapido.main.domain.entity.OrderSheet;
import com.cartrapido.main.domain.entity.Product;
import com.cartrapido.main.service.CartService;
import com.cartrapido.main.service.OrderNumService;
import com.cartrapido.main.service.OrderSheetService;
import com.cartrapido.main.service.ProductService;
import com.cartrapido.main.web.dto.CartDTO;
import com.cartrapido.main.web.dto.OrderNumDTO;
import com.cartrapido.main.web.dto.OrderSheetDTO;
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

    @Autowired
    private OrderSheetService orderSheetService;

    @Autowired
    private OrderNumService orderNumService;

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
    @GetMapping("/clientMart/{mart}")
    public String mart(@PathVariable("mart") String mart, Model model,
                       @PageableDefault(size=3, sort = "productId", direction = Sort.Direction.DESC)Pageable pageable) {
        List<ProductDTO> productList = productService.getStoreProductList(mart, pageable);
        Page<Product> products = productService.pagingStoreProduct(mart, pageable);
        int startPage = Math.max(0, products.getPageable().getPageNumber()-2);
        int endPage = Math.min(products.getPageable().getPageNumber()+2, products.getTotalPages()-1);
        int endEndPage = products.getTotalPages()-1;

        model.addAttribute("productList",productList);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        model.addAttribute("endEndPage",endEndPage);
        model.addAttribute("mart",mart);
        model.addAttribute("products",products);

        return "/clientWebBody/mart";
    }

    //페이징 적용
    //마트별로(where store), 카테고리별로(where category) 상품보여줌 //
    @GetMapping("/clientMart/{mart}/{category}")
    public String category( @PathVariable("mart") String mart, @PathVariable("category") String category, Model model,
                            @PageableDefault(size=3, sort = "productId", direction = Sort.Direction.DESC)Pageable pageable) {

        List<ProductDTO> productList = productService.getCategoryProductList(mart, category, pageable);
        Page<Product> products = productService.pagingCategoryProduct(mart, category, pageable);

        int startPage = Math.max(0, products.getPageable().getPageNumber()-2);
        int endPage = Math.min(products.getPageable().getPageNumber()+2, products.getTotalPages()-1);
        int endEndPage = products.getTotalPages()-1;

        model.addAttribute("productList",productList);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        model.addAttribute("endEndPage",endEndPage);
        model.addAttribute("mart",mart);
        model.addAttribute("category",category);
        model.addAttribute("products",products);

        return "/clientWebBody/category";
    }

    @PostMapping("/clientMart/putInCart")
    @ResponseBody
    public void putInCart(@RequestParam Long productId, HttpSession session,
                          @RequestParam(required=true,defaultValue="1") Integer amount) {

        ProductDTO productDTO = productService.getProductInfo(productId);
        SessionUser user = (SessionUser) session.getAttribute("user");
        String userEmail = user.getEmail();
        System.out.println(productDTO.getStore()+" 상품을 카트에 넣는다");
        CartDTO cartDTO = new CartDTO(userEmail,productId,amount,
                productDTO.getProductName(), productDTO.getProductPrice(),
                productDTO.getImage(), productDTO.getStore());

        if(cartService.checkCart(productId, userEmail)==true) cartService.saveCart(cartDTO);
    }

    @PostMapping("/clientWeb/pushOrder")
    @ResponseBody /*@ModelAttribute List<CartDTO> cartList*/
    public void pushOrder(@RequestParam (value = "chbox[]") List<String> checkArr ) {
        CartDTO cartDTO = cartService.getCartIdInfo(Long.parseLong(checkArr.get(0)));
        String userEmail =cartDTO.getUserEmail();
        OrderNumDTO orderNumDTO = new OrderNumDTO(
                userEmail,null,0,0
        );
        //OrderNum 저장
        OrderNum orderNum = orderNumService.saveOrderNum(orderNumDTO);
        Long orderNum1 = orderNum.getOrderNum();
        //OrderSheet 저장
        for(String cartId : checkArr) {
            cartDTO = cartService.getCartIdInfo(Long.parseLong(cartId));
            OrderSheetDTO orderSheetDTO = new OrderSheetDTO(
                    orderNum1, orderNumDTO.getUserEmail(),
                    cartDTO.getProductId(), cartDTO.getAmount()
            );
            orderSheetService.saveOrderSheet(orderSheetDTO);
            cartService.deleteCart(Long.parseLong(cartId));
        }
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
        SessionUser user = (SessionUser) session.getAttribute("user");
        String userEmail = user.getEmail();
        List<CartDTO> cartList = cartService.getCartList(userEmail);

        if(cartList.size()!=0) {
            for (CartDTO cartDTO : cartList) {
                System.out.println("카트 아이디 " + cartDTO.getCartId());
                System.out.println("카트 주인 " + cartDTO.getUserEmail());
                System.out.println("들어갈 상품 번호 " + cartDTO.getProductId());
                System.out.println("들어갈 상품 번호 " + cartDTO.getImage());
            }
            Long firstCartId = cartList.get(0).getCartId();
            Long lastCartId = cartList.get(cartList.size() - 1).getCartId();
            model.addAttribute("firstCartId", firstCartId);
            model.addAttribute("lastCartId", lastCartId);
            model.addAttribute("cartList", cartList);
        }else{
            model.addAttribute("firstCartId", 0);
            model.addAttribute("lastCartId", 0);
            model.addAttribute("cartList", cartList);
        }

        return "/clientWebBody/shoppingCart";
    }

    @GetMapping("/clientChatting")
    public String clientChatting() {

        return "/clientWebBody/clientChatting.html";
    }










}
