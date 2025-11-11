package com.parket.webproject.controller.controller.mypage;

import com.parket.webproject.cofig.author.PrincipalDetails;
import com.parket.webproject.domain.PayMethod;
import com.parket.webproject.domain.User;
import com.parket.webproject.dto.ProductDTO;
import com.parket.webproject.repository.member.MemberRepository;
import com.parket.webproject.service.ProductService;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/mypage")
@Log4j2
public class MyPageController {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MemberRepository memberRepository;
    private final ProductService productService;

    public MyPageController(BCryptPasswordEncoder bCryptPasswordEncoder, MemberRepository memberRepository, ProductService productService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.memberRepository = memberRepository;
        this.productService = productService;
    }

    @GetMapping("/info")
    public void info(@AuthenticationPrincipal PrincipalDetails principal, Model model) {
        log.info("마이페이지 진입");
        User user = principal.getUser(); // 로그인한 사용자 정보 가져오기
        model.addAttribute("user", user);
    }

    @GetMapping("/infoModify")
    public void infoModifyGet(@AuthenticationPrincipal PrincipalDetails principal, Model model) {
        log.info("내 정보 수정 페이지 입장");
        User user = principal.getUser(); // 로그인한 사용자 정보 가져오기
        model.addAttribute("user", user);
    }

    @PostMapping("/infoModify")
    public String infoModifyPost(@AuthenticationPrincipal PrincipalDetails principal, @ModelAttribute User newInfo) {
        User currentUser = principal.getUser(); // 로그인한 사용자 정보 가져오기
        // 비밀번호 변경 처리 (비어있지 않을 경우만)
        if (newInfo.getPassword() != null && !newInfo.getPassword().trim().isEmpty()) {
            String encodedPassword = bCryptPasswordEncoder.encode(newInfo.getPassword());
            currentUser.setPassword(encodedPassword);
        }
        // 주소 변경 처리 (비어있지 않을 경우만)
        if (newInfo.getAddress() != null && !newInfo.getAddress().trim().isEmpty()) {
            currentUser.setAddress(newInfo.getAddress());
        }

        memberRepository.save(currentUser);
        return "redirect:/mypage/info";
    }


    @GetMapping("/saleComplete")
    public void saleComplete(@AuthenticationPrincipal PrincipalDetails principal, Model model) {
        log.info("판매완료 도서 진입");
        User user = principal.getUser(); // 로그인한 사용자 정보 가져오기
        model.addAttribute("user", user);
    }

    @GetMapping("/writeList")
    public void writeList(@AuthenticationPrincipal PrincipalDetails principal, Model model) {
        log.info("작성한 글 진입");
        User user = principal.getUser(); // 로그인한 사용자 정보 가져오기
        model.addAttribute("user", user);

        List<ProductDTO> products = productService.findProductsByUserId(user.getId());
        model.addAttribute("products", products);
        model.addAttribute("productCount", products.size());
    }

    @GetMapping("/orderList")
    public void orderList(@AuthenticationPrincipal PrincipalDetails principal, Model model) {
        log.info("나의 주문내역 진입");
        User user = principal.getUser(); // 로그인한 사용자 정보 가져오기
        model.addAttribute("user", user);
    }

    //마이페이지 - 결제수단 등록
    @GetMapping("/payManagement")
    public String payManagement(@AuthenticationPrincipal PrincipalDetails principal, Model model) {
        log.info("결제수단 관리 진입");
        User user = principal.getUser(); // 로그인한 사용자 정보 가져오기
        model.addAttribute("user", user);

        model.addAttribute("payMethod", new PayMethod());
        return "mypage/payManagement";
    }
}
