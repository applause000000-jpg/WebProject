package com.parket.webproject.controller.controller.foreign;

import com.parket.webproject.domain.Product;
import com.parket.webproject.dto.BookDTO;
import com.parket.webproject.dto.ProductDTO;
import com.parket.webproject.repository.BookRepository;
import com.parket.webproject.service.BookService;
import com.parket.webproject.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/foreign")
public class ForeignController {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookService bookService;
    @Autowired
    private ProductService productService;

    @GetMapping("/list")
    public String showDomesticList(Model model) {
        model.addAttribute("books", bookRepository.findByForeignAll());
        return "foreign/list";  // templates/foreign/list.html
    }
    @GetMapping("/search")
    public String searchDomesticList(String type,String bookType,String keyword,Model model) {
        if ("ta".equals(type)) {
            model.addAttribute("books", bookRepository.findByALLSearchAll("%" + bookType + "%","%" + keyword + "%"));
        } else if ("t".equals(type)) {
            model.addAttribute("books", bookRepository.findByTitleSearchAll("%" + bookType + "%","%" + keyword + "%"));
        } else if ("a".equals(type)) {
            model.addAttribute("books", bookRepository.findByAuthorSearchAll("%" + bookType + "%","%" + keyword + "%"));
        }
        model.addAttribute("keyword", keyword);
        return "foreign/list";  // templates/foreign/list.html
    }
    @GetMapping("/detail")
    public String detail(Long bno, Model model) {
        BookDTO book = bookService.findBookById(bno);
        model.addAttribute("book", book);
        // product repository로 변경해야할수도있음.
        List<Product> matchedProducts = bookRepository.detailProduct(book.getTitle(), book.getAuthor());
        List<ProductDTO> lists = new ArrayList<>();
        for (Product p : matchedProducts) {
            lists.add(productService.entityToDto(p));
        }
        model.addAttribute("lists", lists);
        return "foreign/detail"; // templates/use/list.html 파일을 렌더링
    }
}
