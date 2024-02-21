package com.mayank.cart.service;

import com.mayank.cart.dto.AddToCartRequest;
import com.mayank.cart.dto.Cart;
import com.mayank.cart.dto.CartItem;
import com.mayank.cart.dto.DeleteItemRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CartService {

    boolean addToCart(AddToCartRequest request) throws Exception;
    Cart getCartByUserEmail(String userEmail) throws Exception;
    List<CartItem> getAllCartItemsByID(Integer cartId) throws Exception;
    Optional<CartItem> findProductInCart(String productId) throws Exception;
    boolean deleteItem(DeleteItemRequest request) throws Exception;
}
