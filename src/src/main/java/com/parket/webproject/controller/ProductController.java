package com.parket.webproject.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.parket.webproject.cofig.author.PrincipalDetails;
import com.parket.webproject.domain.User;
import com.parket.webproject.dto.BookDTO;
import com.parket.webproject.dto.ProductDTO;
import com.parket.webproject.service.BookService;
import com.parket.webproject.service.ProductService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@Log4j2
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private BookService bookService;

    @GetMapping("/write")
    public void registerGet() {
        log.info("상품 등록 페이지 진입");
    }

    @PostMapping("/write")
    public String registerPost(@RequestParam(required = false) List<String> conditions, ProductDTO productDTO, @AuthenticationPrincipal PrincipalDetails principal) {
        // conditions 리스트 → JSON 문자열
        if (conditions != null && !conditions.isEmpty()) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                String json = objectMapper.writeValueAsString(conditions);
                productDTO.setConditions(json);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        productDTO.setUsername(principal.getUsername());
        Long productId = productService.insertProduct(productDTO);
        log.info("상품 등록 완료: productId=" + productId);
        return "redirect:/product/list";
    }

    @GetMapping("/list" )
    public void list(Model model, @AuthenticationPrincipal PrincipalDetails principal) {
        log.info("상품 리스트");
        List<ProductDTO> products = productService.findAllProducts();
        model.addAttribute("products", products);
        model.addAttribute("principal", principal);
    }

    @GetMapping("/select")
    @ResponseBody
    public List<BookDTO> submitMessage(@RequestParam("message") String message) throws IOException {
        List<BookDTO> books = bookService.CrawlSelectBook(message);
        return books;
    }

    @GetMapping( "/modify")
    public void readProduct(@RequestParam("productId") Long productId, Model model) {
        log.info("상품 수정 페이지 진입");
        ProductDTO productDTO = productService.findProductById(productId);
        model.addAttribute("product", productDTO);
    }

    // 상품 수정 처리
    @PostMapping("/modify")
    public String modifyProduct(ProductDTO productDTO, @RequestParam(value = "conditions", required = false) List<String> conditions,
                                    @AuthenticationPrincipal PrincipalDetails principal) {
        // conditions 리스트 → JSON 문자열
        if (conditions != null && !conditions.isEmpty()) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                String json = objectMapper.writeValueAsString(conditions);
                productDTO.setConditions(json);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        User user = principal.getUser();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
        productService.updateProduct(productDTO);
        log.info("상품 수정 완료");
        return isAdmin ? "redirect:/product/list" : "redirect:/mypage/writeList";
    }

    // 상품 삭제 처리
    @GetMapping("/remove")
    public String removeProduct(@RequestParam("productId") Long productId, @AuthenticationPrincipal PrincipalDetails principal) {
        log.info("상품 삭제 시작");
        productService.deleteProduct(productId);
        User user = principal.getUser();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
        log.info("상품 삭제 완료");
        return isAdmin ? "redirect:/product/list" : "redirect:/mypage/writeList";
    }

}