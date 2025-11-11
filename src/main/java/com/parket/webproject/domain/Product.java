package com.parket.webproject.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@ToString(exclude = {"user", "crawlBook"})
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;
    @Column(nullable = false, length = 500)
    private String title;
    @Column(nullable = false)
    private Long price;
    @Column(nullable = false)
    private String realPrice;
    @Column(nullable = false, length = 100)
    private String author;
    private String publisher;
    //    제품 상태 필드
    @Column(columnDefinition = "JSON")
    private String conditions;
    //    제품 판매 여부
    @Column(nullable = false)
    private Boolean isSold = false;
    @Column(length = 500)
    private String bookImageUrl;

    public void change(Long price, String conditions) {
        this.price = price;
        this.conditions = conditions;
    }
}