package com.mayank.cart.service;

import com.mayank.cart.dto.UpdateCartRequest;
import com.mayank.cart.dto.Cart;
import com.mayank.cart.dto.CartItem;
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
    public Optional<CartItem> findProductInCart(Integer cartId, String productId) throws Exception {
        try {
            return cartItemRepository.findByProductIdAndCartId(cartId, productId);
        } catch(Exception e) {
            logger.log(Level.WARNING, "Encountered a problem while fetching item by product Id -- findProductInCart in CartService. - " + e.getMessage());
            throw new Exception("Error while fetching Item in cart by product Id and Cart Id.");
        }
    }
    @Override
    public boolean updateCartItem(UpdateCartRequest request, String userEmail) throws Exception {
        try {
            Cart cart = getCartByUserEmail(userEmail);
            String productId = request.getProductId();
            Integer quantity = request.getQuantity();
            long status = 1;
            if(quantity == 0) status = cartItemRepository.deleteByProductIdAndCartId(cart.getId(), productId);
            else {
                CartItem item;
                Optional<CartItem> opt = findProductInCart(cart.getId(), productId);
                if(opt.isEmpty()) {
                    item = new CartItem();
                    item.setProductId(productId);
                    item.setCart(cart);
                } else {
                    item = opt.get();
                }
                item.setQuantity(quantity);
                cartItemRepository.save(item);
            }
            return status != 0;
        } catch(Exception e) {
            logger.log(Level.WARNING, "Encountered a problem while updating cart -- updateCartItem in CartService. - " + e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public boolean deleteCartByUserEmail(String userEmail) throws Exception {
        try {
            Optional<Cart> opt = cartRepository.findByUserEmail(userEmail);
            if(opt.isEmpty()) {
                logger.log(Level.WARNING, "Cart already empty.");
                return false;
            }
            cartRepository.deleteByUserEmail(userEmail);
            return cartRepository.findByUserEmail(userEmail).isEmpty();
        } catch(Exception e) {
            logger.log(Level.WARNING, "Encountered a problem while deleting address -- deleteAddress in AddressService. - " + e.getMessage());
            throw new Exception("Error while deleting address.");
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
