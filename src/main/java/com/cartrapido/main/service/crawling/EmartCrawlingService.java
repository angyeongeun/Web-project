package com.cartrapido.main.service.crawling;

import com.cartrapido.main.domain.repository.ProductRepository;
import com.cartrapido.main.web.dto.ProductDTO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmartCrawlingService {

    @Autowired
    private ProductRepository productRepository;

//    private List<ProductDTO> emartProductList = new ArrayList<ProductDTO>();
//    private static int count;

    private String store = "emart";

    private String[] array_url = {
            "http://emart.ssg.com/category/main.ssg?dispCtgId=6000095739", //EMART_FRUIT_URL
            "http://emart.ssg.com/category/main.ssg?dispCtgId=6000095740", //EMART_VEGETABLE_URL
            "http://emart.ssg.com/category/main.ssg?dispCtgId=6000095498", //EMART_RICEANDNUTS_URL
            "http://emart.ssg.com/category/main.ssg?dispCtgId=6000095499", //EMART_MEATANDEGGS_URL
            "http://emart.ssg.com/category/main.ssg?dispCtgId=6000095501", //EMART_MILK_URL
            "http://emart.ssg.com/category/main.ssg?dispCtgId=6000095500", //EMART_SEAFOOD_URL
            "http://emart.ssg.com/category/main.ssg?dispCtgId=6000095502", //EMART_CONVENIENCEFOOD_URL
            "http://emart.ssg.com/category/main.ssg?dispCtgId=6000095503", //EMART_KIMCHIANDMEALKIT_URL
            "http://emart.ssg.com/category/main.ssg?dispCtgId=6000095507", //EMART_DRINK_URL
            "http://emart.ssg.com/category/main.ssg?dispCtgId=6000095508", //EMART_COFFEEANDTEA_URL
            "http://emart.ssg.com/category/main.ssg?dispCtgId=6000095504", //EMART_NOODLEANDFASTFOOD_URL
            "http://emart.ssg.com/category/main.ssg?dispCtgId=6000095509", //EMART_OILANDSAUCE_URL
            "http://emart.ssg.com/category/main.ssg?dispCtgId=6000095505", //EMART_SNACK_URL
            "http://emart.ssg.com/category/main.ssg?dispCtgId=6000095506", //EMART_BAKERY_URL
            "http://emart.ssg.com/category/main.ssg?dispCtgId=6000095510", //EMART_HEALTHFOOD_URL
    };

    private String[] array_category = {
            "Fruit",            //EMART_FRUIT_URL
            "Vegetable",        //EMART_VEGETABLE_URL
            "Rice&Nuts",        //EMART_RICEANDNUTS_URL
            "Meat&Eggs",        //EMART_MEATANDEGGS_URL
            "Milk",             //EMART_MILK_URL
            "Seafood",          //EMART_SEAFOOD_URL
            "ConvenienceFood",  //EMART_CONVENIENCEFOOD_URL
            "SideDish&MealKit", //EMART_KIMCHIANDMEALKIT_URL
            "Drink",            //EMART_DRINK_URL
            "Coffee&Tea",       //EMART_COFFEEANDTEA_URL
            "Noodles&FastFood", //EMART_NOODLEANDFASTFOOD_URL
            "Oil&Sauce",        //EMART_OILANDSAUCE_URL
            "Snack",            //EMART_SNACK_URL
            "Bakery",           //EMART_BAKERY_URL
            "HealthFood"       //EMART_HEALTHFOOD_URL
    };

    //@PostConstruct
    public void getEmartDatas() {

        //날짜 비교해서 크롤링하기
//        List<LocalDateTime> list = productRepository.martDate(store);
//
//        if (list.size() != 0) {
//            LocalDate latest = LocalDate.from(list.get(0));
//            LocalDate today = LocalDate.from(LocalDateTime.now());
//
//            if (today.isAfter(latest)) {
//                productRepository.deleteByStore(store);
//                crawling();
//            }
//        }
//        else {
            crawling();
//        }
    }

    public void crawling() {
        System.out.println(store);
        for (int j = 0; j < array_url.length; j++) {
            try {
                //크롤링 차단 막기
                Document doc = Jsoup.connect(array_url[j])
                        .userAgent("Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1667.0 Safari/537.36")
                        .header("scheme", "https")
                        .header("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                        .header("accept-encoding", "gzip, deflate, br")
                        .header("accept-language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7,es;q=0.6")
                        .header("cache-control", "no-cache")
                        .header("pragma", "no-cache")
                        .header("upgrade-insecure-requests", "1")
                        .get();
                Elements elements = doc.select("div#ty_thmb_view ul");

                for (Element element : elements) {
                    int i;
                    for (i = 0; i < elements.select("li.cunit_t232").size(); i++) {

                        String itemId = elements.select("input[name=attnTgtIdnfNo1]").get(i).attr("value");

                        String productName = elements.select("em.tx_en").get(i).text();

                        String stringProductPrice = elements.select("em.ssg_price").get(i).text();
                        String[] ProductPriceSplit = stringProductPrice.split(",");
                        String productPrice = "";
                        for (String str : ProductPriceSplit) {
                            productPrice = productPrice + str;
                        }

                        String image = elements.select("a.clickable img.i1").get(i).attr("src");

                        //Productcontent 가져오기
                        String productcontent = "";

                        ProductDTO productDTO = ProductDTO.builder()
                                .itemId(itemId)
                                .productName(productName)
                                .productPrice(Integer.parseInt(productPrice))
                                .productQty(10)
                                .productContent(productcontent)
                                .store(store)
                                .category(array_category[j])
                                .image(image)
                                .wishPoint(0)
                                .build();

                        productRepository.save(productDTO.toEntity()).getProductId();
                        //emartProductList.add(productDTO);

                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
