package com.parket.webproject.service;

import com.parket.webproject.cofig.author.PrincipalDetails;
import com.parket.webproject.domain.Cart;
import com.parket.webproject.domain.User;
import com.parket.webproject.dto.CartDTO;

import java.util.List;

public interface CartService {
    void addToCart(PrincipalDetails principalDetails, CartDTO cartDTO);
    List<Cart> findByUserId(long userId);
    void deleteSelected(List<Integer> cartIds);
    List<Cart> getSelectedCartItems(List<Integer> cartIds);

}
