package com.parket.webproject.repository;

import com.parket.webproject.domain.PayHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PayHistoryRepository extends JpaRepository<PayHistory, Long> {
    List<PayHistory> findByOrderNo(String orderNo);
}
