package com.parket.webproject.repository;

import com.parket.webproject.domain.PayMethod;
import com.parket.webproject.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PayMethodRepository extends JpaRepository<PayMethod, Long> {
    List<PayMethod> findByUser(User user);
    // 결제하기 버튼 클릭시 유저가 등록했는지 안뜨는지 뜨기
    boolean existsByUser(User user);
    Optional<PayMethod> findMethodByUser(User user);

    User user(User user);
}
