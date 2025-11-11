package com.parket.webproject.Runner;


import com.parket.webproject.repository.BookRepository;
import com.parket.webproject.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookRunner implements CommandLineRunner {

    private final BookService bookService;

    @Override
    public void run(String... args) throws Exception {
        bookService.CrawlBooks();
    }
}
