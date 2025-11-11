package com.parket.webproject.service;

import com.parket.webproject.domain.PayMethod;
import com.parket.webproject.domain.User;

public interface PayService {
    boolean hasPayMethod(User user);

}
