package com.parket.webproject.dto;

import com.parket.webproject.domain.PayMethod;
import com.parket.webproject.domain.Product;
import com.parket.webproject.domain.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;

public class PayHistoryDTO {
    private Long purchaseId;
    private Long userId;
    private Long productId;
    private Long paymentId;

    private LocalDateTime created_at;
    private String orderNo;
}
