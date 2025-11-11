package com.parket.webproject.dto;

import com.parket.webproject.domain.PayMethod;
import com.parket.webproject.domain.Product;
import com.parket.webproject.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayHistoryDTO {
    private Long purchaseId;
    private Long userId;
    private Long productId;
    private Long paymentId;

    private String bookImageUrl;
    private String title;
    private String author;
    private String publisher;
    private Long price;

    private LocalDateTime created_at;
    private String orderNo;
}
