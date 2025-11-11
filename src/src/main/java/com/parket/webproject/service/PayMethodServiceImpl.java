package com.parket.webproject.service;

import com.parket.webproject.cofig.author.PrincipalDetails;
import com.parket.webproject.domain.PayMethod;
import com.parket.webproject.domain.User;
import com.parket.webproject.repository.PayMethodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PayMethodServiceImpl implements PayMethodService {

    private final PayMethodRepository payMethodRepository;

    @Override
    public String registerPayMethod(@ModelAttribute PayMethod payMethod) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        User user = principalDetails.getUser();

        // 로그인 유저 연결
        payMethod.setUser(user);
        payMethodRepository.save(payMethod);

        // 등록 완료 후 다시 장바구니
        return "redirect:/cart/list";
    }

    // 결제하기 버튼 - 결제수단 있는지 없는지 확인
    @Override
    public boolean hasPayMethod(User user) {
        return payMethodRepository.existsByUser(user);
    }

    @Override
    public Optional<PayMethod> getPayMethodByUser(User user) {
        return payMethodRepository.findMethodByUser(user);
    }


}
