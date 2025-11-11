package com.parket.webproject.dto;

import com.parket.webproject.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// 장바구니 사용안거치고 사용할  DTO
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DirectOrderItemDTO {
    private ProductDTO product;
    private int quantity;
}
