package com.parket.webproject.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "payMethod")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    // 나중에 join 수정해야함!!!!!!!!!!!
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    private String methodType;
    private String bankName;
    private String accountNumber;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime created_at;

}
