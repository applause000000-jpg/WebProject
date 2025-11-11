package com.parket.webproject.controller.controller.member;

import com.parket.webproject.cofig.author.PrincipalDetails;
import com.parket.webproject.domain.User;
import com.parket.webproject.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberRepository memberRepository;

    @Autowired
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // 회원가입 페이지
    @GetMapping("/join")
    public void join() {
    }

    // 회원가입 처리
    @Transactional
    @PostMapping("/register")
    public String register(User user) {
        String encPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encPassword);
        user.setRole("USER");

        try {
            memberRepository.save(user);
            System.out.println("회원 저장 완료: " + user.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/member/complete";
    }

    // 회원가입 완료 페이지
    @GetMapping("/complete")
    public String complete() {
        return "member/complete";
    }

    // 로그인 페이지
    @GetMapping("/login")
    public void login() {
    }

    // 아이디 중복 체크
    @GetMapping("/check-username")
    @ResponseBody
    public Map<String, Boolean> checkUsername(@RequestParam String username) {
        boolean exists = memberRepository.existsByUsername(username);
        return Map.of("exists", exists);
    }

    // 로그인 상태 확인용 (디버깅)
    @GetMapping("/check")
    public String myPageInfo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("현재 로그인 사용자: " + auth.getName());
        System.out.println("권한: " + auth.getAuthorities());
        return "redirect:/";
    }

    // 내 정보 페이지 (info.html 연결)
    @GetMapping("/info")
    public String infoPage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        if (username == null || username.equals("anonymousUser")) {
            // 로그인 안 되어 있으면 로그인 페이지로 이동
            return "redirect:/member/login";
        }

        // DB에서 현재 로그인한 사용자 정보 조회
        User user = memberRepository.findByUsername(username).orElse(null);

        // null 예외 방지
        if (user == null) {
            return "redirect:/member/login";
        }

        // HTML로 user 객체 전달
        model.addAttribute("user", user);

        return "mypage/info";  // templates/mypage/info.html
    }
    @GetMapping("/mypage/info")
    public String info(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        User user = principalDetails.getUser(); //  로그인한 사용자의 실제 User 엔티티
        model.addAttribute("user", user);
        return "mypage/info";
    }

}
