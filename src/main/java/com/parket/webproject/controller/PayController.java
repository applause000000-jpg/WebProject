package com.parket.webproject.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.parket.webproject.cofig.author.PrincipalDetails;
import com.parket.webproject.domain.PayHistory;
import com.parket.webproject.domain.PayMethod;
import com.parket.webproject.domain.User;
import com.parket.webproject.dto.DirectOrderItemDTO;
import com.parket.webproject.dto.OrderResultDTO;
import com.parket.webproject.dto.ProductDTO;
import com.parket.webproject.repository.PayHistoryRepository;
import com.parket.webproject.repository.PayMethodRepository;
import com.parket.webproject.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class PayController {

    private final CartService cartService;
    private final PayService payMethodService;
    private final PayHistoryService payHistoryService;
    private final PayMethodRepository payMethodRepository;
    private final PayHistoryRepository payHistoryRepository;
    private final NotificationService notificationService;
    private final OrderService orderService;
    private final ProductService productService;

    /* (장바구니 or 바로구매 공통) */
    @PostMapping("/pay")
    public String orderPay(
            @RequestParam(required = false) String cartIds,
            @RequestParam(required = false) Long productId,
            @RequestParam(defaultValue = "1") int quantity,
            @AuthenticationPrincipal PrincipalDetails principal,
            Model model
    ) throws Exception {

        if (principal == null || principal.getUser() == null) {
            return "redirect:/member/login";
        }
        User user = principal.getUser();

        boolean hasPayMethod = payMethodService.hasPayMethod(user);
        Optional<PayMethod> payMethodOpt = payMethodRepository.findMethodByUser(user);
        model.addAttribute("payMethod", payMethodOpt.orElse(null));

        List<?> orderItems;
        int totalQuantity = 0;
        long totalPrice = 0L;

        if (cartIds != null && !cartIds.isBlank()) {
            ObjectMapper mapper = new ObjectMapper();
            List<Integer> cartIdList = mapper.readValue(cartIds, new TypeReference<List<Integer>>() {});
            var selectedItems = cartService.getSelectedCartItems(cartIdList);

            totalQuantity = selectedItems.stream().mapToInt(c -> c.getQuantity()).sum();
            totalPrice = selectedItems.stream().mapToLong(c -> c.getProduct().getPrice() * c.getQuantity()).sum();
            orderItems = selectedItems;

            model.addAttribute("cartIdsJson", cartIds);
        } else if (productId != null) {
            ProductDTO product = productService.findProductById(productId);
            if (product == null) throw new IllegalArgumentException("상품을 찾을 수 없습니다. ID=" + productId);

            DirectOrderItemDTO orderItem = new DirectOrderItemDTO(product, quantity);
            orderItems = List.of(orderItem);
            totalQuantity = quantity;
            totalPrice = product.getPrice() * quantity;
        } else {
            throw new IllegalArgumentException("결제할 상품 정보가 없습니다.");
        }

        model.addAttribute("orderItems", orderItems);
        model.addAttribute("totalQuantity", totalQuantity);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("member", user);
        model.addAttribute("hasPayMethod", hasPayMethod);

        return "order/pay";
    }

    /* 결제수단 유무 확인 */
    @PostMapping("/pay_check")
    @ResponseBody
    public ResponseEntity<String> checkPayMethod() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        User user = principalDetails.getUser();

        boolean hasPayMethod = payMethodService.hasPayMethod(user);
        return ResponseEntity.ok(hasPayMethod ? "OK" : "NO");
    }

    /* 결제 완료 처리 */
    @PostMapping("/complete")
    public String completePayment(
            @AuthenticationPrincipal PrincipalDetails principal,
            @RequestParam(value = "cartIds", required = false) String cartIdsJson,
            @RequestParam(value = "productId", required = false) Long productId,
            @RequestParam(value = "quantity", defaultValue = "1") int quantity,
            RedirectAttributes redirectAttributes
    ) throws Exception {

        if (principal == null || principal.getUser() == null) {
            return "redirect:/login";
        }
        User user = principal.getUser();

        ObjectMapper mapper = new ObjectMapper();
        List<Integer> cartIds = new ArrayList<>();

        if (cartIdsJson != null && !cartIdsJson.isBlank()) {
            if (cartIdsJson.trim().startsWith("[")) {
                cartIds = mapper.readValue(cartIdsJson, new TypeReference<List<Integer>>() {});
            } else {
                cartIds.add(Integer.parseInt(cartIdsJson.replaceAll("\"", "").trim()));
            }
        }

        // 통합 메서드
        OrderResultDTO result = orderService.completeOrder(user, cartIds, productId, quantity);

        redirectAttributes.addAttribute("orderNo", result.getOrderNo());
        redirectAttributes.addFlashAttribute("totalQuantity", result.getTotalQuantity());
        redirectAttributes.addFlashAttribute("totalPrice", result.getTotalPrice());

        return "redirect:/order/complete";
    }

    /* 결제 완료 페이지 */
    @GetMapping("/complete")
    public String completePayment(@RequestParam("orderNo") String orderNo, Model model) {
        List<PayHistory> histories = payHistoryRepository.findByOrderNo(orderNo);

        model.addAttribute("orderNo", orderNo);
        model.addAttribute("histories", histories);

        return "order/complete";
    }
}
