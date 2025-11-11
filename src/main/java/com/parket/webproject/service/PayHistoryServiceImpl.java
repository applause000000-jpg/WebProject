package com.parket.webproject.service;

import com.parket.webproject.domain.*;
import com.parket.webproject.dto.PayHistoryDTO;
import com.parket.webproject.dto.ProductDTO;
import com.parket.webproject.repository.PayHistoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor

public class PayHistoryServiceImpl implements PayHistoryService {

    final PayHistoryRepository payHistoryRepository;

    @Override
    @Transactional
    public void savePayHistory(User user, PayMethod payMethod, List<Cart> selectedItems, String orderNo) {
        // 주문번호 생성
//        String orderNo = "ORD-" + System.currentTimeMillis();
        for (Cart cart : selectedItems) {
            PayHistory history = PayHistory.builder()
                    .orderNo(orderNo)
                    .user(user)
                    .product(cart.getProduct())
                    .created_at(LocalDateTime.now())
                    .payMethod(payMethod)
                    .build();
            payHistoryRepository.save(history);
        }
    }
    //구매하기 바로갈때 ..
    @Override
    public void saveDirectPayHistory(User user, PayMethod payMethod, Product product, int quantity, String orderNo) {
        PayHistory history = PayHistory.builder()
                .orderNo(orderNo)
                .user(user)
                .product(product)
                .payMethod(payMethod)
                .created_at(LocalDateTime.now())
                .build();

        payHistoryRepository.save(history);
    }

    @Override
    public List<PayHistoryDTO> findPayHistoryByUserId(Long userId) {
        List<PayHistory> historys = payHistoryRepository.findByUserId(userId);
        List<PayHistoryDTO> dtos = new ArrayList<>();
        for (PayHistory history : historys) {
            dtos.add(entityToDto(history));
        }

        return dtos;
    }
}
