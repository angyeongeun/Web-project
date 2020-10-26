package com.cartrapido.main.service.crawling;

import com.cartrapido.main.domain.repository.ProductRepository;
import com.cartrapido.main.web.dto.ProductDTO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CUCrawlingService {

    @Autowired
    private ProductRepository productRepository;

//    private List<ProductDTO> cuProductList = new ArrayList<ProductDTO>();
//    private static int count;

    private String store = "cu";

    private String[] array_url = {
            "http://cu.bgfretail.com/product/product.do?category=product&depth2=4&depth3=1", //CU_ConvenienceFood_URL
            "http://cu.bgfretail.com/product/product.do?category=product&depth2=4&depth3=2", //CU_FastFood_URL
            "http://cu.bgfretail.com/product/product.do?category=product&depth2=4&depth3=3", //CU_Snack_URL
            "http://cu.bgfretail.com/product/product.do?category=product&depth2=4&depth3=4", //CU_IceCream_URL
            "http://cu.bgfretail.com/product/product.do?category=product&depth2=4&depth3=5", //CU_Food_URL
            "http://cu.bgfretail.com/product/product.do?category=product&depth2=4&depth3=6", //CU_Drink_URL
    };

    private String[] array_category = {
            "ConvenienceFood",  //CU_ConvenienceFood_URL
            "FastFood",         //CU_FastFood_URL
            "Snack",            //CU_Snack_URL
            "IceCream",         //CU_IceCream_URL
            "Food",             //CU_Food_URL
            "Drink",            //CU_Drink_URL
    };

    //WebDriver
    private WebDriver driver;
    private WebElement element;

    //Properties
    public static final String WEB_DRIVER_ID = "webdriver.chrome.driver";
    public static final String WEB_DRIVER_PATH = "C:/drivers/chromedriver.exe";
    //크롤링 할 URL
    private String base_url;

    //@PostConstruct
    public void getCUDatas() {

        //productRepository.deleteAll();

        System.out.println(store);

        //System Property SetUp
        System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);

        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless");

        //Driver SetUp
        driver = new ChromeDriver(options);
        convenienceFoodCrawling();

        driver = new ChromeDriver(options);
        fastFoodCrawling();

        driver = new ChromeDriver(options);
        snackCrawling();

        driver = new ChromeDriver(options);
        iceCreamCrawling();

        driver = new ChromeDriver(options);
        foodCrawling();

        driver = new ChromeDriver(options);
        drinkCrawling();
    }

    public void convenienceFoodCrawling() {
        try {
            //get page (= 브라우저에서 url을 주소창에 넣은 후 request 한 것과 같다)
            driver.get("http://cu.bgfretail.com/product/product.do?category=product&depth2=4&depth3=1");
            element = driver.findElement(By.id("setD"));
            element.click();

            Thread.sleep(3000);

            String html = driver.getPageSource();

            Document doc = Jsoup.parse(html);
            //System.out.println(doc);

            Elements elements = doc.select("div.prodListWrap");

            //System.out.println(elements);
            int i;
            for (i = 0; i < elements.select("div.photo").size(); i++) {
                //System.out.println(i);

                String productName = elements.select("p.prodName span").get(i).text();
                //System.out.println(productName);

                String stringProductPrice = elements.select("p.prodPrice span").get(i).text();
                String[] ProductPriceSplit = stringProductPrice.split(",");
                String productPrice = "";
                for (String str : ProductPriceSplit) {
                    productPrice = productPrice + str;
                }
                //System.out.println(productPrice);

                String image = elements.select("div.photo a img").get(i).attr("src");
                //System.out.println(image);

                String itemId = "";
                String productcontent = "";

                ProductDTO productDTO = ProductDTO.builder()
                        .itemId(itemId)
                        .productName(productName)
                        .productPrice(Integer.parseInt(productPrice))
                        .productQty(10)
                        .productContent(productcontent)
                        .store(store)
                        .category("ConvenienceFood")
                        .image(image)
                        .wishPoint(0)
                        .build();
                productRepository.save(productDTO.toEntity()).getProductId();
                //cuProductList.add(productDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.close();
        }
    }
    public void fastFoodCrawling() {
        try {

            driver.get("http://cu.bgfretail.com/product/product.do?category=product&depth2=4&depth3=2");
            element = driver.findElement(By.id("setD"));
            element.click();

            Thread.sleep(3000);

            String html = driver.getPageSource();

            Document doc = Jsoup.parse(html);
            //System.out.println(doc);

            Elements elements = doc.select("div.prodListWrap");

            //System.out.println(elements);
            int i;
            for (i = 0; i < elements.select("div.photo").size(); i++) {
                //System.out.println(i);

                String productName = elements.select("p.prodName span").get(i).text();
                //System.out.println(productName);

                String stringProductPrice = elements.select("p.prodPrice span").get(i).text();
                String[] ProductPriceSplit = stringProductPrice.split(",");
                String productPrice = "";
                for (String str : ProductPriceSplit) {
                    productPrice = productPrice + str;
                }
                //System.out.println(productPrice);

                String image = elements.select("div.photo a img").get(i).attr("src");
                //System.out.println(image);

                String itemId = "";

                String productcontent = "";

                ProductDTO productDTO = ProductDTO.builder()
                        //.productId((long)count++)
                        .productName(productName)
                        .productPrice(Integer.parseInt(productPrice))
                        .productQty(10)
                        .productContent(productcontent)
                        .store(store)
                        .category("FastFood")
                        .image(image)
                        .wishPoint(0)
                        .build();

                productRepository.save(productDTO.toEntity()).getProductId();
                //cuProductList.add(productDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.close();
        }
    }
    public void snackCrawling() {
        try {
            //get page (= 브라우저에서 url을 주소창에 넣은 후 request 한 것과 같다)
            driver.get("http://cu.bgfretail.com/product/product.do?category=product&depth2=4&depth3=3");
            Thread.sleep(10000);

            element = driver.findElement(By.id("setA"));
            element.click();

            Thread.sleep(5000);

            String html = driver.getPageSource();

            Document doc = Jsoup.parse(html);
            //System.out.println(doc);

            Elements elements = doc.select("div.prodListWrap");

            //System.out.println(elements);
            int i;
            for (i = 0; i < elements.select("div.photo").size(); i++) {
                //System.out.println(i);

                String productName = elements.select("p.prodName span").get(i).text();
                //System.out.println(productName);

                String stringProductPrice = elements.select("p.prodPrice span").get(i).text();
                String[] ProductPriceSplit = stringProductPrice.split(",");
                String productPrice = "";
                for (String str : ProductPriceSplit) {
                    productPrice = productPrice + str;
                }
                //System.out.println(productPrice);

                String image = elements.select("div.photo a img").get(i).attr("src");
                //System.out.println(image);

                String itemId = "";

                String productcontent = "";

                ProductDTO productDTO = ProductDTO.builder()
                        .itemId(itemId)
                        .productName(productName)
                        .productPrice(Integer.parseInt(productPrice))
                        .productQty(10)
                        .productContent(productcontent)
                        .store(store)
                        .category("Snack")
                        .image(image)
                        .wishPoint(0)
                        .build();

                productRepository.save(productDTO.toEntity()).getProductId();
                //cuProductList.add(productDTO);
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.close();
        }
    }
    public void iceCreamCrawling() {
        try {
            //get page (= 브라우저에서 url을 주소창에 넣은 후 request 한 것과 같다)
            driver.get("http://cu.bgfretail.com/product/product.do?category=product&depth2=4&depth3=4");
//            element = driver.findElement(By.id("setD"));
//            element.click();

            Thread.sleep(3000);

            String html = driver.getPageSource();

            Document doc = Jsoup.parse(html);
            //System.out.println(doc);

            Elements elements = doc.select("div.prodListWrap");

            //System.out.println(elements);
            int i;
            for (i = 0; i < elements.select("div.photo").size(); i++) {
                //System.out.println(i);

                String productName = elements.select("p.prodName span").get(i).text();
                //System.out.println(productName);

                String stringProductPrice = elements.select("p.prodPrice span").get(i).text();
                String[] ProductPriceSplit = stringProductPrice.split(",");
                String productPrice = "";
                for (String str : ProductPriceSplit) {
                    productPrice = productPrice + str;
                }
                //System.out.println(productPrice);

                String image = elements.select("div.photo a img").get(i).attr("src");
                //System.out.println(image);

                String itemId = "";

                String productcontent = "";

                ProductDTO productDTO = ProductDTO.builder()
                        .itemId(itemId)
                        .productName(productName)
                        .productPrice(Integer.parseInt(productPrice))
                        .productQty(10)
                        .productContent(productcontent)
                        .store(store)
                        .category("IceCream")
                        .image(image)
                        .wishPoint(0)
                        .build();

                productRepository.save(productDTO.toEntity()).getProductId();
                //cuProductList.add(productDTO);
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.close();
        }
    }
    public void foodCrawling() {
        try {
            //get page (= 브라우저에서 url을 주소창에 넣은 후 request 한 것과 같다)
            driver.get("http://cu.bgfretail.com/product/product.do?category=product&depth2=4&depth3=5");
//            element = driver.findElement(By.id("setB"));
//            element.click();

            Thread.sleep(10000);

            String html = driver.getPageSource();

            Document doc = Jsoup.parse(html);
            //System.out.println(doc);

            Elements elements = doc.select("div.prodListWrap");

            //System.out.println(elements);
            int i;
            for (i = 0; i < elements.select("div.photo").size(); i++) {
                //System.out.println(i);

                String productName = elements.select("p.prodName span").get(i).text();
                //System.out.println(productName);

                String stringProductPrice = elements.select("p.prodPrice span").get(i).text();
                String[] ProductPriceSplit = stringProductPrice.split(",");
                String productPrice = "";
                for (String str : ProductPriceSplit) {
                    productPrice = productPrice + str;
                }
                //System.out.println(productPrice);

                String image = elements.select("div.photo a img").get(i).attr("src");
                //System.out.println(image);

                String itemId = "";

                String productcontent = "";

                ProductDTO productDTO = ProductDTO.builder()
                        .itemId(itemId)
                        .productName(productName)
                        .productPrice(Integer.parseInt(productPrice))
                        .productQty(10)
                        .productContent(productcontent)
                        .store(store)
                        .category("Food")
                        .image(image)
                        .wishPoint(0)
                        .build();

                productRepository.save(productDTO.toEntity()).getProductId();
                //cuProductList.add(productDTO);
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.close();
        }
    }
    public void drinkCrawling() {
        try {
            //get page (= 브라우저에서 url을 주소창에 넣은 후 request 한 것과 같다)
            driver.get("http://cu.bgfretail.com/product/product.do?category=product&depth2=4&depth3=6");
//            element = driver.findElement(By.id("setC"));
//            element.click();

            Thread.sleep(10000);

            String html = driver.getPageSource();

            Document doc = Jsoup.parse(html);
            //System.out.println(doc);

            Elements elements = doc.select("div.prodListWrap");

            //System.out.println(elements);
            int i;
            for (i = 0; i < elements.select("div.photo").size(); i++) {
                //System.out.println(i);

                String productName = elements.select("p.prodName span").get(i).text();
                //System.out.println(productName);

                String stringProductPrice = elements.select("p.prodPrice span").get(i).text();
                String[] ProductPriceSplit = stringProductPrice.split(",");
                String productPrice = "";
                for (String str : ProductPriceSplit) {
                    productPrice = productPrice + str;
                }
                //System.out.println(productPrice);

                String image = elements.select("div.photo a img").get(i).attr("src");
                //System.out.println(image);

                String itemId = "";

                String productcontent = "";

                ProductDTO productDTO = ProductDTO.builder()
                        .itemId(itemId)
                        .productName(productName)
                        .productPrice(Integer.parseInt(productPrice))
                        .productQty(10)
                        .productContent(productcontent)
                        .store(store)
                        .category("Drink")
                        .image(image)
                        .wishPoint(0)
                        .build();

                productRepository.save(productDTO.toEntity()).getProductId();
                //cuProductList.add(productDTO);
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.close();
        }
    }

}
