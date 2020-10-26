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
public class GSCrawlingService {

    @Autowired
    private ProductRepository productRepository;

//    private List<ProductDTO> gsProductList = new ArrayList<ProductDTO>();;
//    private static int count;

    private String store = "gs25";

    //WebDriver
    private WebDriver driver;
    private WebElement element;

    //Properties
    public static final String WEB_DRIVER_ID = "webdriver.chrome.driver";
    public static final String WEB_DRIVER_PATH = "C:/drivers/chromedriver.exe";
    //크롤링 할 URL
    private String base_url;

    //@PostConstruct
    public void getGSDatas() {

        System.out.println(store);

        //System Property SetUp
        System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);

        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless");

        //Driver SetUp
        driver = new ChromeDriver(options);
        lunchBoxCrawling();
        driver = new ChromeDriver(options);
        gimbapCrawling(); //
        driver = new ChromeDriver(options);
        sandwichCrawling();
        driver = new ChromeDriver(options);
        convenienceFoodCrawling();

        driver = new ChromeDriver(options);
        drinkCrawling();
        driver = new ChromeDriver(options);
        milkProductsCrawling();
        driver = new ChromeDriver(options);
        snackCrawling();
        driver = new ChromeDriver(options);
        instantNoodlesCrawling();

    }

    public void lunchBoxCrawling() {
        try {

            driver.get("http://gs25.gsretail.com/gscvs/ko/products/youus-freshfood");

            element = driver.findElement(By.className("srv_ico2"));
            element.click();
            Thread.sleep(3000);

            String html = driver.getPageSource();

            Document doc = Jsoup.parse(html);
            //System.out.println(doc);

            Elements elements = doc.select("ul.prod_list");

            int i;
            for (i = 0; i < elements.select("li").size(); i++) {
                //System.out.println(i);

                String productName = elements.select("p.tit").get(i).text();
                //System.out.println(productName);

                String stringProductPrice = elements.select("span.cost").get(i).text();
                String[] productPriceSplit = stringProductPrice.split(",");
                String productPrice = "";
                for (String str : productPriceSplit) {
                    productPrice = productPrice + str;
                }
                productPrice = productPrice.replaceAll("원", "");

                //System.out.println(productPrice);

                String image = elements.select("p.img img").get(i).attr("src");
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
                        .category("LunchBox")
                        .image(image)
                        .wishPoint(0)
                        .build();

                productRepository.save(productDTO.toEntity()).getProductId();
                //gsProductList.add(productDTO);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.close();
        }
    }
    public void gimbapCrawling() {
        try {

            driver.get("http://gs25.gsretail.com/gscvs/ko/products/youus-freshfood");

            element = driver.findElement(By.className("srv_ico3"));
            element.click();
            Thread.sleep(3000);

            String html = driver.getPageSource();

            Document doc = Jsoup.parse(html);
            //System.out.println(doc);

            Elements elements = doc.select("ul.prod_list");

            int i;
            for (i = 0; i < elements.select("li").size(); i++) {
                //System.out.println(i);

                String productName = elements.select("p.tit").get(i).text();
                //System.out.println(productName);

                String stringProductPrice = elements.select("span.cost").get(i).text();
                String[] productPriceSplit = stringProductPrice.split(",");
                String productPrice = "";
                for (String str : productPriceSplit) {
                    productPrice = productPrice + str;
                }
                productPrice = productPrice.replaceAll("원", "");

                //System.out.println(productPrice);

                String image = elements.select("p.img img").get(i).attr("src");
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
                        .category("Gimbap")
                        .image(image)
                        .wishPoint(0)
                        .build();

                productRepository.save(productDTO.toEntity()).getProductId();
                //gsProductList.add(productDTO);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.close();
        }
    }
    public void sandwichCrawling() {
        try {

            driver.get("http://gs25.gsretail.com/gscvs/ko/products/youus-freshfood");

            element = driver.findElement(By.className("srv_ico4"));
            element.click();
            Thread.sleep(3000);

            String html = driver.getPageSource();

            Document doc = Jsoup.parse(html);
            //System.out.println(doc);

            Elements elements = doc.select("ul.prod_list");

            int i;
            for (i = 0; i < elements.select("li").size(); i++) {
                //System.out.println(i);

                String productName = elements.select("p.tit").get(i).text();
                //System.out.println(productName);

                String stringProductPrice = elements.select("span.cost").get(i).text();
                String[] productPriceSplit = stringProductPrice.split(",");
                String productPrice = "";
                for (String str : productPriceSplit) {
                    productPrice = productPrice + str;
                }
                productPrice = productPrice.replaceAll("원", "");

                //System.out.println(productPrice);

                String image = elements.select("p.img img").get(i).attr("src");
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
                        .category("Sandwich&Hamburger")
                        .image(image)
                        .wishPoint(0)
                        .build();

                productRepository.save(productDTO.toEntity()).getProductId();
                //gsProductList.add(productDTO);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.close();
        }
    }
    public void convenienceFoodCrawling() {
        try {

            driver.get("http://gs25.gsretail.com/gscvs/ko/products/youus-freshfood");

            element = driver.findElement(By.className("srv_ico5"));
            element.click();
            Thread.sleep(3000);

            String html = driver.getPageSource();

            Document doc = Jsoup.parse(html);
            //System.out.println(doc);

            Elements elements = doc.select("ul.prod_list");

            int i;
            for (i = 0; i < elements.select("li").size(); i++) {
                //System.out.println(i);

                String productName = elements.select("p.tit").get(i).text();
                //System.out.println(productName);

                String stringProductPrice = elements.select("span.cost").get(i).text();
                String[] productPriceSplit = stringProductPrice.split(",");
                String productPrice = "";
                for (String str : productPriceSplit) {
                    productPrice = productPrice + str;
                }
                productPrice = productPrice.replaceAll("원", "");

                //System.out.println(productPrice);

                String image = elements.select("p.img img").get(i).attr("src");
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
                //gsProductList.add(productDTO);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.close();
        }
    }

    public void drinkCrawling() {
        try {

            driver.get("http://gs25.gsretail.com/gscvs/ko/products/youus-different-service");

            element = driver.findElement(By.className("srv_ico2"));
            element.click();
            Thread.sleep(3000);

            String html = driver.getPageSource();

            Document doc = Jsoup.parse(html);
            //System.out.println(doc);

            Elements elements = doc.select("ul.prod_list");

            int i;
            for (i = 0; i < elements.select("li").size(); i++) {
                //System.out.println(i);

                String productName = elements.select("p.tit").get(i).text();
                //System.out.println(productName);

                String stringProductPrice = elements.select("span.cost").get(i).text();
                String[] productPriceSplit = stringProductPrice.split(",");
                String productPrice = "";
                for (String str : productPriceSplit) {
                    productPrice = productPrice + str;
                }
                productPrice = productPrice.replaceAll("원", "");

                //System.out.println(productPrice);

                String image = elements.select("p.img img").get(i).attr("src");
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
                //gsProductList.add(productDTO);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.close();
        }
    }
    public void milkProductsCrawling() {
        try {

            driver.get("http://gs25.gsretail.com/gscvs/ko/products/youus-different-service");

            element = driver.findElement(By.className("srv_ico3"));
            element.click();
            Thread.sleep(3000);

            String html = driver.getPageSource();

            Document doc = Jsoup.parse(html);
            //System.out.println(doc);

            Elements elements = doc.select("ul.prod_list");

            int i;
            for (i = 0; i < elements.select("li").size(); i++) {
                //System.out.println(i);

                String productName = elements.select("p.tit").get(i).text();
                //System.out.println(productName);

                String stringProductPrice = elements.select("span.cost").get(i).text();
                String[] productPriceSplit = stringProductPrice.split(",");
                String productPrice = "";
                for (String str : productPriceSplit) {
                    productPrice = productPrice + str;
                }
                productPrice = productPrice.replaceAll("원", "");

                //System.out.println(productPrice);

                String image = elements.select("p.img img").get(i).attr("src");
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
                        .category("MilkProducts")
                        .image(image)
                        .wishPoint(0)
                        .build();

                productRepository.save(productDTO.toEntity()).getProductId();
                //gsProductList.add(productDTO);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.close();
        }
    }
    public void snackCrawling() {
        try {

            driver.get("http://gs25.gsretail.com/gscvs/ko/products/youus-different-service");

            element = driver.findElement(By.className("srv_ico4"));
            element.click();
            Thread.sleep(3000);

            String html = driver.getPageSource();

            Document doc = Jsoup.parse(html);
            //System.out.println(doc);

            Elements elements = doc.select("ul.prod_list");

            int i;
            for (i = 0; i < elements.select("li").size(); i++) {
                //System.out.println(i);

                String productName = elements.select("p.tit").get(i).text();
                //System.out.println(productName);

                String stringProductPrice = elements.select("span.cost").get(i).text();
                String[] productPriceSplit = stringProductPrice.split(",");
                String productPrice = "";
                for (String str : productPriceSplit) {
                    productPrice = productPrice + str;
                }
                productPrice = productPrice.replaceAll("원", "");

                //System.out.println(productPrice);

                String image = elements.select("p.img img").get(i).attr("src");
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
                //gsProductList.add(productDTO);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.close();
        }
    }
    public void instantNoodlesCrawling() {
        try {

            driver.get("http://gs25.gsretail.com/gscvs/ko/products/youus-different-service");

            element = driver.findElement(By.className("srv_ico5"));
            element.click();
            Thread.sleep(3000);

            String html = driver.getPageSource();

            Document doc = Jsoup.parse(html);
            //System.out.println(doc);

            Elements elements = doc.select("ul.prod_list");

            int i;
            for (i = 0; i < elements.select("li").size(); i++) {
                //System.out.println(i);

                String productName = elements.select("p.tit").get(i).text();
                //System.out.println(productName);

                String stringProductPrice = elements.select("span.cost").get(i).text();
                String[] productPriceSplit = stringProductPrice.split(",");
                String productPrice = "";
                for (String str : productPriceSplit) {
                    productPrice = productPrice + str;
                }
                productPrice = productPrice.replaceAll("원", "");

                //System.out.println(productPrice);

                String image = elements.select("p.img img").get(i).attr("src");
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
                        .category("InstantFood&Noodles")
                        .image(image)
                        .wishPoint(0)
                        .build();

                productRepository.save(productDTO.toEntity()).getProductId();
                //gsProductList.add(productDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.close();
        }
    }

}
