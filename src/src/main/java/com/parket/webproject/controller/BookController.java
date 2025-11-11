package com.parket.webproject.controller;


import com.parket.webproject.domain.CrawlBook;
import com.parket.webproject.dto.BookDTO;
import com.parket.webproject.repository.BookRepository;
import com.parket.webproject.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {
    private final BookRepository bookRepository;
    private final BookService bookService;

    @GetMapping("/view")
    public String showBooks(Model model) {
        model.addAttribute("books", bookRepository.findAll());
        return "BookHtml";
    }
    @GetMapping("/books/category")
    public String getBooksByCategory(@RequestParam String keyword, Model model) {
        List<CrawlBook> books = bookRepository.findByCategoryContaining("%" + keyword + "%");
        model.addAttribute("books", books);
        return "domestic/list :: bookList";
    }
    @PostMapping("/view")
    public String submitMessage(@RequestParam("message") String message, Model model) throws IOException {
        List<BookDTO> books = bookService.CrawlSelectBook(message);
        model.addAttribute("selbooks", books);
        return "BookHtml";
    }

//    @PostMapping("/view")
//    public String selectBook(){
//
//    }


}
