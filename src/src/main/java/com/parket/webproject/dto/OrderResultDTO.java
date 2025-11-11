package com.parket.webproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor

public class OrderResultDTO {
    private final String orderNo;
    private final int totalQuantity;
    private final long totalPrice;
}
