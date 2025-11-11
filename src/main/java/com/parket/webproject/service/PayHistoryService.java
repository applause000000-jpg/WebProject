package com.parket.webproject.service;

import com.parket.webproject.domain.*;
import com.parket.webproject.dto.PayHistoryDTO;

import java.util.List;

public interface PayHistoryService {
    void savePayHistory(User user, PayMethod payMethod, List<Cart> selectedItems, String orderNo);
    void saveDirectPayHistory(User user, PayMethod payMethod, Product product, int quantity, String orderNo);
    List<PayHistoryDTO> findPayHistoryByUserId(Long userId);

    default PayHistoryDTO entityToDto(PayHistory payHistory) {
        if(payHistory == null) return null;

        Product product = payHistory.getProduct();
        return PayHistoryDTO.builder()
                .purchaseId(payHistory.getPurchaseId())
                .userId(payHistory.getUser() != null ? payHistory.getUser().getId() : null)
                .productId(product != null ? product.getProductId() : null)
                .paymentId(payHistory.getPayMethod() != null ? payHistory.getPayMethod().getPaymentId() : null)
                .created_at(payHistory.getCreated_at())
                .orderNo(payHistory.getOrderNo())
                // Product 정보
                .bookImageUrl(product != null ? product.getBookImageUrl() : null)
                .title(product != null ? product.getTitle() : null)
                .author(product != null ? product.getAuthor() : null)
                .publisher(product != null ? product.getPublisher() : null)
                .price(product != null ? product.getPrice() : null)
                .build();
    }
}