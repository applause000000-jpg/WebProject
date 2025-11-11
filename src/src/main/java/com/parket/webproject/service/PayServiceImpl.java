package com.parket.webproject.service;

import com.parket.webproject.domain.User;
import com.parket.webproject.domain.PayMethod;
import com.parket.webproject.repository.PayMethodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PayServiceImpl implements PayService {
    private final PayMethodRepository payMethodRepository;
    @Override
    public boolean hasPayMethod(User user) {
        return payMethodRepository.existsByUser(user);
    }
}
