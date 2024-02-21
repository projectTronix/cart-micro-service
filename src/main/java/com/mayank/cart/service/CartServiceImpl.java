package com.mayank.cart.service;

import com.mayank.cart.dto.AddToCartRequest;
import com.mayank.cart.dto.Cart;
import com.mayank.cart.dto.CartItem;
import com.mayank.cart.dto.DeleteItemRequest;
import com.mayank.cart.repository.CartItemRepository;
import com.mayank.cart.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
@Transactional
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService{
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    LogManager logManager = LogManager.getLogManager();
    Logger logger = logManager.getLogger(Logger.GLOBAL_LOGGER_NAME);
    @Override
    public boolean addToCart(AddToCartRequest request) throws Exception {
        try {
            Cart cart = getCartByUserEmail(request.getUserEmail());
            String productId = request.getProductId();
            Integer quantity = request.getQuantity();
            CartItem item;
            Optional<CartItem> opt = findProductInCart(productId);
            if(opt.isEmpty()) {
                item = new CartItem();
                item.setProductId(productId);
                item.setQuantity(quantity);
                item.setCart(cart);
            } else {
                item = opt.get();
                item.setQuantity(item.getQuantity() + quantity);
            }
            cartItemRepository.save(item);
            return true;
        } catch(Exception e) {
            logger.log(Level.WARNING, "Encountered a problem while add to cart -- addToCart in CartService. - " + e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    public Optional<CartItem> findProductInCart(String productId) throws Exception {
        try {
            return cartItemRepository.findByProductId(productId);
        } catch(Exception e) {
            logger.log(Level.WARNING, "Encountered a problem while fetching item by product Id -- findProductInCart in CartService. - " + e.getMessage());
            throw new Exception("Error while fetching Item in cart by product Id.");
        }
    }

    @Override
    public boolean deleteItem(DeleteItemRequest request) throws Exception {
        try {
            String userEmail = request.getUserEmail();
            String productId = request.getProductId();
            Integer quantity = request.getQuantity();
            Cart cart = getCartByUserEmail(userEmail);
            if(cart.getItems().isEmpty()) {
                throw new Exception("Cart is empty.");
            }
            Optional<CartItem> opt = findProductInCart(productId);
            boolean isEmpty = opt.isEmpty();
            Integer quantityPresent = opt.get().getQuantity();
            if(isEmpty || quantityPresent < quantity) {
                throw new Exception("Product doesn't exist in cart or Quantity passed is more than Quantity present in cart.");
            }
            long status = 1;
            if(quantity == quantityPresent) status = cartItemRepository.deleteByProductIdAndCartId(productId, cart.getId());
            else {
                Integer newQuantityInCart = quantityPresent - quantity;
                cartItemRepository.updateQuantityByProductIdAndCartId(newQuantityInCart, productId, cart.getId());
            }
            return status != 0;
        } catch(Exception e) {
            logger.log(Level.WARNING, e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Cart getCartByUserEmail(String userEmail) throws Exception {
        try {
            Optional<Cart> opt = cartRepository.findByUserEmail(userEmail);
            if(opt.isEmpty()) {
                Cart cart = new Cart();
                cart.setUserEmail(userEmail);
                return cart;
            }
            return opt.get();
        } catch(Exception e) {
            logger.log(Level.WARNING, "Encountered a problem while fetching cart by user email -- getCartByUserEmail in CartService. - " + e.getMessage());
            throw new Exception("Error while fetching Cart of user.");
        }
    }

    @Override
    public List<CartItem> getAllCartItemsByID(Integer cartId) throws Exception {
        try {
            List<CartItem> cartItems = cartItemRepository.findAllByCartId(cartId);
            if(cartItems.isEmpty()) {
                throw new Exception("Cart is Empty.");
            }
            return cartItems;
        } catch(Exception e) {
            logger.log(Level.WARNING, "Encountered a problem while fetching cart items -- getAllCartItemsByID in CartService. - " + e.getMessage());
            throw new Exception("Error while fetching cart of user.");
        }
    }
}
