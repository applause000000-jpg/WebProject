package com.parket.webproject.service;

import com.parket.webproject.domain.PayMethod;
import com.parket.webproject.domain.User;

import java.util.Optional;

public interface PayMethodService {
    String registerPayMethod(PayMethod payMethod);
    boolean hasPayMethod(User user);
    Optional<PayMethod> getPayMethodByUser(User user);
}
