package com.parket.webproject;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.junit.jupiter.api.Test;

import java.io.IOException;

public class CrawlTest {


    @Test
    public void testCrawlAladinBestsellers() throws IOException {
        // 알라딘 베스트셀러 페이지 URL
        String url = "https://www.aladin.co.kr/shop/common/wbest.aspx?BestType=Bestseller";

        // User-Agent 설정 (403 방지)
        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0")
                .get();

        // 책 정보가 담긴 요소 선택
        Elements bookElements = doc.select(".ss_book_box");
//        #Myform > div:nth-child(3) > table > tbody > tr > td:nth-child(3) > table > tbody > tr
//        td:nth-child(1) > div:nth-child(1) > ul > li:nth-child(3) > a:nth-child(1)
        for (Element book : bookElements) {

            Element img = book.selectFirst(".front_cover");
            String imageUrl = img != null ? img.attr("src") : null;

            String title = book.select(".bo3").text();
            Elements authors = book.select("ul > li:nth-child(3) > a");
            String author="";
            for (Element a : authors) {
                author += a.text();
            }
//            List<String> author = new ArrayList<>();
//            for (Element a : authors) {
//                author.add(a.text());
//            }
            String price = book.select(".ss_p2").text();

            System.out.println("이미지 : " + imageUrl);
            System.out.println("제목 : " + title);
            System.out.println("저자 : " + author);
            System.out.println("가격 : " + price);
            System.out.println("------------------");
        }
    }

}
