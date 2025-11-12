package com.parket.webproject.controller;

import com.parket.webproject.cofig.author.PrincipalDetailService;
import com.parket.webproject.cofig.author.PrincipalDetails;
import com.parket.webproject.domain.PayMethod;
import com.parket.webproject.domain.User;
import com.parket.webproject.service.PayMethodService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/payMethod")
public class PayMethodContoller {

    private final PayMethodService payMethodService;
    private final PrincipalDetailService principalDetailService;

    // 등록 폼 보여주기
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("payMethod", new PayMethod());
        return "payRegister"; // templates/pay/register.html
    }

    // 등록 처리
    @PostMapping("/register")
    public String registerPayMethod(@ModelAttribute PayMethod payMethod, @AuthenticationPrincipal PrincipalDetails principal) {

        // 로그인 유저 가져오기
        if (principal == null || principal.getUser() == null) {
            // 로그인 안 된 경우 (혹시 SecurityConfig에서 인증 보호 안 되어 있을 때 대비)
            return "redirect:/member/login";
        }
        User user = principal.getUser();

        // 유저 연결 후 저장
        payMethod.setUser(user);
        payMethodService.registerPayMethod(payMethod);

        // 등록 후 다시 장바구니로 ..
        return "redirect:/cart/list";
    }
}
