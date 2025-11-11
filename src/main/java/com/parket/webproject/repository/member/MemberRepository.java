package com.parket.webproject.repository.member;

import com.parket.webproject.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<User, Long> {

    // username으로 사용자 조회 (Optional로 감싸 안전하게 반환)
    Optional<User> findByUsername(String username);

    // username 중복 여부 확인
    boolean existsByUsername(String username);
}
