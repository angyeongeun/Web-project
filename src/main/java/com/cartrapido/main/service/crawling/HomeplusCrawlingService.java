package com.cartrapido.main.service.crawling;

import com.cartrapido.main.domain.repository.ProductRepository;
import com.cartrapido.main.web.dto.ProductDTO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class HomeplusCrawlingService {

    @Autowired
    private ProductRepository productRepository;

//    private List<ProductDTO> homeplusProductList = new ArrayList<ProductDTO>();
//    private static int count;

    private String store = "homeplus";

    private String[] array_url = {
            "http://www.homeplus.co.kr/app.exhibition.category.Category.ghs?comm=category.list&cid=60002", //Homeplus_Fruit_url
            "http://www.homeplus.co.kr/app.exhibition.category.Category.ghs?comm=category.list&cid=60037", //Homeplus_Vegetable_url
            "http://www.homeplus.co.kr/app.exhibition.category.Category.ghs?comm=category.list&cid=60098", //Homeplus_Rice_url
            "http://www.homeplus.co.kr/app.exhibition.category.Category.ghs?comm=category.list&cid=60113", //Homeplus_Nuts_url
            "http://www.homeplus.co.kr/app.exhibition.category.Category.ghs?comm=category.list&cid=60126", //Homeplus_Meat&Eggs_url
            "http://www.homeplus.co.kr/app.exhibition.category.Category.ghs?comm=category.list&cid=60161", //Homeplus_Seafood_url
            "http://www.homeplus.co.kr/app.exhibition.category.Category.ghs?comm=category.list&cid=60199", //Homeplus_Milk_url
            "http://www.homeplus.co.kr/app.exhibition.category.Category.ghs?comm=category.list&cid=60236", //Homeplus_SideDish&FrozenFood_url
            "http://www.homeplus.co.kr/app.exhibition.category.Category.ghs?comm=category.list&cid=60316", //Homeplus_ConvenienceFood_url
            "http://www.homeplus.co.kr/app.exhibition.category.Category.ghs?comm=category.list&cid=60320", //Homeplus_Noodles&InstantRice_url
            "http://www.homeplus.co.kr/app.exhibition.category.Category.ghs?comm=category.list&cid=60342", //Homeplus_snack&cereal_url
            "http://www.homeplus.co.kr/app.exhibition.category.Category.ghs?comm=category.list&cid=60385", //Homeplus_Drink_url
            "http://www.homeplus.co.kr/app.exhibition.category.Category.ghs?comm=category.list&cid=60447", //Homeplus_Sauce&Can_url

    };

    private String[] array_category = {
            "Fruit",
            "Vegetable",
            "Rice",
            "Nuts",
            "Meat&Eggs",
            "Seafood",
            "Milk",
            "SideDish&FrozenFood",
            "ConvenienceFood",
            "Noodles&InstantRice",
            "Snack&Cereal",
            "Drink",
            "Sauce&Can"
    };

    //@PostConstruct
    public void getHomeplusDatas() {

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
//        }else {
            crawling();
//        }

    }

    public void crawling() {
        System.out.println(store);

        for (int j = 0; j < array_url.length; j++) {

            try {

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

                Elements elements = doc.select("div#ui-nowsale");
                //System.out.println(array_category[j]);
                int i;
                for (i = 0; i < elements.select("ul.fix-ty2 a.thumb").size(); i++) {
                    //System.out.println(i);

                    String productName = elements.select("a.name").get(i).text();
                    //System.out.println(productName);

                    String stringProductPrice = elements.select("strong.buy").get(i).text();
                    String[] ProductPriceSplit = stringProductPrice.split(",");
                    String productPrice = "";
                    for (String str : ProductPriceSplit) {
                        productPrice = productPrice + str;
                    }
                    //System.out.println(productPrice);

                    String image = elements.select("a.thumb img[width=176]").get(i).attr("src");
                    //System.out.println(image);

                    String stringItemId = elements.select("input[name=order_qty]").get(i).attr("id");
                    String itemId = stringItemId.substring(2, 11);
                    //System.out.println(itemId);

                    String productcontent = "";
//                    String array_contentUrl = "http://www.homeplus.co.kr/app.product.GoodDetail.ghs?comm=usr.detail&good_id="+itemId;
//                    Document doc1 = Jsoup.connect(array_contentUrl).get();
//                    productcontent = doc1.select("table.tb-info").html();
                    //System.out.println(productcontent);

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
                    //homeplusProductList.add(productDTO);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
