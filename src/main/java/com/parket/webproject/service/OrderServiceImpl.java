package com.parket.webproject.service;

import com.parket.webproject.domain.Cart;
import com.parket.webproject.domain.PayMethod;
import com.parket.webproject.domain.Product;
import com.parket.webproject.domain.User;
import com.parket.webproject.dto.OrderResultDTO;
import com.parket.webproject.repository.PayMethodRepository;
import com.parket.webproject.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final CartService cartService;
    private final PayHistoryService payHistoryService;
    private final NotificationService notificationService;
    private final ProductRepository productRepository;
    private final PayMethodRepository payMethodRepository;

    /**
     * 장바구니 결제와 바로구매 결제를 통합한 메서드
     */
    @Override
    public OrderResultDTO completeOrder(User user, List<Integer> cartIds, Long productId, int quantity) {
        // 공통 결제 수단
        List<PayMethod> payMethods = payMethodRepository.findByUser(user);
        if (payMethods.isEmpty()) {
            throw new IllegalStateException("결제수단이 없습니다.");
        }
        PayMethod payMethod = payMethods.get(0);

        String orderNo = "ORD-" + System.currentTimeMillis();

        int totalQuantity = 0;
        long totalPrice = 0L;

        // 장바구니 결제
        if (cartIds != null && !cartIds.isEmpty()) {
            List<Cart> selectedItems = cartService.getSelectedCartItems(cartIds);

            // 결제 내역 저장
            payHistoryService.savePayHistory(user, payMethod, selectedItems, orderNo);

            // 수량 및 가격 계산
            totalQuantity = selectedItems.stream().mapToInt(Cart::getQuantity).sum();
            totalPrice = selectedItems.stream()
                    .mapToLong(cart -> cart.getProduct().getPrice() * cart.getQuantity())
                    .sum();

            // 알림 생성 및 상태 업데이트
            for (Cart cart : selectedItems) {
                Product product = cart.getProduct();
                product.setIsSold(true);
                notificationService.createNotification(
                        product.getUser(),
                        product.getTitle() + " 책이 판매 완료되었습니다!"
                );
            }

            // 장바구니 삭제
            cartService.deleteSelected(cartIds);
        }

        // 바로결제 시에!!!!!!!!!
        else if (productId != null) {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다. ID=" + productId));

            payHistoryService.saveDirectPayHistory(user, payMethod, product, quantity, orderNo);

            totalQuantity = quantity;
            totalPrice = product.getPrice() * quantity;

            product.setIsSold(true);
            notificationService.createNotification(
                    product.getUser(),
                    product.getTitle() + " 책이 판매 완료되었습니다!"
            );
        } else {
            throw new IllegalArgumentException("결제할 상품 정보가 없습니다.");
        }

        return new OrderResultDTO(orderNo, totalQuantity, totalPrice);
    }
}
