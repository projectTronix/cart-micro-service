package com.mayank.cart.controller;

import com.mayank.cart.dto.*;
import com.mayank.cart.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

@RestController
@RequiredArgsConstructor
@RequestMapping("cart")
@Validated
public class CartController {
    private final CartService cartService;
    private final LogManager logManager = LogManager.getLogManager();
    private final Logger logger = logManager.getLogger(Logger.GLOBAL_LOGGER_NAME);
    @GetMapping("/view/{id}")
    public ResponseEntity<List<CartItem>> viewCart(@PathVariable("id") String userEmail) {
        try {
            Cart cart = cartService.getCartByUserEmail(userEmail);
            List<CartItem> items = cartService.getAllCartItemsByID(cart.getId());
            logger.log(Level.INFO, "Cart of user fetched Successfully.");
            return new ResponseEntity<>(items, HttpStatus.OK);
        } catch(Exception e) {
            logger.log(Level.WARNING, "Encountered a problem while fetching cart of user - viewCart in CartController " + e.getMessage());
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }
    }
    @PostMapping("/update")
    public CustomResponse updateCartItem(@RequestBody @Valid UpdateCartRequest request) {
        try {
            boolean status = cartService.updateCartItem(request);
            if(!status) {
                throw new Exception();
            }
            logger.log(Level.INFO, "Product updated Successfully in cart.");
            return new CustomResponse("Product updated Successfully in cart.", HttpStatus.OK);
        }
        catch(Exception e) {
            logger.log(Level.WARNING, "Encountered a problem while updating the product from cart. - updateCartItem in CartController - " + e.getMessage());
            return new CustomResponse("Encountered a problem while updating the product from cart.", HttpStatus.BAD_REQUEST);
        }
    }

}
