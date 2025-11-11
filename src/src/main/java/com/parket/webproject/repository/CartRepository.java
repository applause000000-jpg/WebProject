package com.parket.webproject.repository;

import com.parket.webproject.domain.Cart;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface CartRepository extends JpaRepository<Cart, Integer> {
    // 유저별 장바구니 목록 조회
    @Query("SELECT c FROM Cart c JOIN FETCH c.product WHERE c.user.id = :userId")
    List<Cart> findByUserId(@Param("userId") long userId);

    // 유저별 장바구니 전체 삭제
    @Transactional
    void deleteByUser_Id(int id);
    
    // 체크박스 된 애들
    List<Cart> findByCartIdIn(List<Integer> cartIds);

    // /mypage/writeList의 삭제 버튼 클릭시
    void deleteByProduct_ProductId(Long productId);
    // 장바구니에 있는지 체크
    boolean existsByUser_IdAndProduct_ProductId(Long userId, Long productId);
}