package com.parket.webproject.dto;

import com.parket.webproject.domain.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;

public class PayMethodDTO {
    private Long paymentId;
    private Long userId;
    private String methodType;
    private String bankName;
    private String accountNumber;
    private LocalDateTime created_at;
}
