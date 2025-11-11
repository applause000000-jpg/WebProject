package com.parket.webproject.controller.controller;

import com.parket.webproject.domain.CrawlBook;
import com.parket.webproject.dto.BookDTO;
import com.parket.webproject.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.awt.print.Book;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private BookRepository bookRepository;
    @GetMapping("/")
    public String home(Model model) {
        List<CrawlBook> allBooks = bookRepository.findAll();
        List<CrawlBook> limitedBooks = allBooks.size() > 10 ? allBooks.subList(0, 10) : allBooks;

        model.addAttribute("books", limitedBooks);
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