package com.parket.webproject.controller.controller;

import com.parket.webproject.domain.CrawlBook;
import com.parket.webproject.domain.Noti;
import com.parket.webproject.dto.BookDTO;
import com.parket.webproject.dto.ProductDTO;
import com.parket.webproject.repository.BookRepository;
import com.parket.webproject.service.NotiService;
import com.parket.webproject.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private NotiService notiService;
    @GetMapping("/")
    public String home(Model model) {
        List<CrawlBook> allBooks = bookRepository.findAll();
        List<CrawlBook> limitedBooks = allBooks.size() > 10 ? allBooks.subList(0, 10) : allBooks;
        List<CrawlBook> newBooks = bookRepository.findByBookNewAll();
        List<CrawlBook> newBooklists = newBooks.size() > 10 ? newBooks.subList(0, 10) : newBooks;
        List<ProductDTO> products = productService.findAllProducts();
        Collections.reverse(products);
        List<ProductDTO> productLists = products.size() > 4 ? products.subList(0, 4) : products;
        for (ProductDTO product : productLists) {
            Long price = product.getPrice();
            String discountText="";
            if (product.getRealPrice() != null ) {
                String realPriceStr = product.getRealPrice();
                Long realPrice = Long.parseLong(realPriceStr.replaceAll("\\D", ""));
                Long discount = price - realPrice;
                discountText = String.format("%,d원", discount);
            }else {
                discountText = "";
            }
            product.setDiscount(discountText);
        }
        List<Noti> noticeList = notiService.getAll();
        if (noticeList == null) noticeList = new ArrayList<>();
        Collections.reverse(noticeList);
        noticeList = noticeList.size() > 2 ? noticeList.subList(0, 2) : noticeList;
        model.addAttribute("noticeList", noticeList);
        model.addAttribute("products", productLists);
        model.addAttribute("books", limitedBooks);
        model.addAttribute("newbooks", newBooklists);
        return "index"; // src/main/resources/templates/index.html
    }

    @GetMapping("/index/category")
    public String getCategoryBooks(@RequestParam String category, Model model) {

        List<CrawlBook> books;
        if ("국내".equals(category)) {
            books = bookRepository.findByDomesticAll(); // 국내 도서 크롤링 함수
        } else if ("해외".equals(category)) {
            books = bookRepository.findByForeignAll(); // 해외 도서 크롤링 함수
        } else{
            books = bookRepository.findAll();
        }
        List<CrawlBook> limitedBooks = books.size() > 10 ? books.subList(0, 10) : books;
        model.addAttribute("books", limitedBooks);
        return "index :: bookSlider";
    }
}