package com.parket.webproject.service;

import com.parket.webproject.domain.User;
import com.parket.webproject.dto.OrderResultDTO;

import java.util.List;

public interface OrderService {
    OrderResultDTO completeOrder(User user, List<Integer> cartIds, Long productId, int quantity);
}
