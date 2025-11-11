package com.parket.webproject.service;

import com.parket.webproject.cofig.author.PrincipalDetails;
import com.parket.webproject.domain.Cart;
import com.parket.webproject.domain.Product;
import com.parket.webproject.domain.User;
import com.parket.webproject.dto.CartDTO;
import com.parket.webproject.repository.CartRepository;
import com.parket.webproject.repository.ProductRepository;
import com.parket.webproject.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service

@Log4j2
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    // 장바구니 추가
    @Override
    public void addToCart(@AuthenticationPrincipal PrincipalDetails principal, CartDTO cartDTO) {
        User user = principal.getUser();
        Long productId = cartDTO.getProductId();
        // 장바구니에 있는지 체크
        boolean alreadyExists = cartRepository.existsByUser_IdAndProduct_ProductId(user.getId(), productId);
        if (alreadyExists) {
            // 이미 있으면 예외 던지기
            throw new IllegalStateException("이미 장바구니에 존재합니다.");
        }

        Product product = productRepository.findById(cartDTO.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        Cart cart = Cart.builder()
                .user(user)
                .product(product)
                .quantity(1)
                .addedAt(LocalDateTime.now())
                .build();

        cartRepository.save(cart);
    }

    @Override
    public List<Cart> findByUserId(long userId) {
        return cartRepository.findByUserId(userId);
    }

    // 장바구니 리스트 뿌리기
    @Transactional
    @Override
    public void deleteSelected(List<Integer> cartIds) {
        cartRepository.deleteAllById(cartIds);
    }

    @Override
    public List<Cart> getSelectedCartItems(List<Integer> cartIds) {
        return cartRepository.findByCartIdIn(cartIds);
    }


}
