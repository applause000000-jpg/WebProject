package com.parket.webproject.controller;

import com.parket.webproject.cofig.author.PrincipalDetails;
import com.parket.webproject.domain.Cart;
import com.parket.webproject.dto.CartDTO;
import com.parket.webproject.service.CartServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
@Log4j2

public class CartController {
    private final CartServiceImpl cartService;

    // 장바구니 추가
    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<String> addToCart( @AuthenticationPrincipal PrincipalDetails principal,
                                             @RequestBody CartDTO cartDTO) {
        cartService.addToCart(principal, cartDTO);
        return ResponseEntity.ok().build();
    }

    //@GetMapping("/list")
    public String goCartList() {
        return "cart/list";
    }

    // 장바구니 리스트
    @GetMapping("/list")
    public String cartList(@AuthenticationPrincipal PrincipalDetails principal, Model model) {
        if (principal == null || principal.getUser() == null) {
            return "redirect:/member/login";
        }

        long userId = principal.getUser().getId();
        List<Cart> cartItems = cartService.findByUserId(userId);

        model.addAttribute("cartItems", cartItems);
        log.info("🧾 장바구니 조회 | userId={}, itemCount={}", userId, cartItems.size());
        return "cart/list";
    }

    //선택 삭제
    @PostMapping("/deleteSelected")
    @ResponseBody
    public ResponseEntity<?> deleteSelected(@RequestBody List<Integer> cartIds) {
        cartService.deleteSelected(cartIds);
        return ResponseEntity.ok().build();
    }
}
