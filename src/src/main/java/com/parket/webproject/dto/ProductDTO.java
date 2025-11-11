package com.parket.webproject.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
    private Long productId;
    @NotEmpty
    private String username;
    @NotEmpty
    private String title;
    @NotNull
    private Long price;
    @NotNull
    private String realPrice;
    @NotEmpty
    private String author;
    private String publisher;
    // 제품 상태
    private String conditions;
    // 판매 여부
    private Boolean isSold;
    private String bookImageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
