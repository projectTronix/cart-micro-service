package com.mayank.cart.controller;

import com.mayank.cart.config.UserService;
import com.mayank.cart.dto.*;
import com.mayank.cart.service.CartService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("cart")
@Validated
public class CartController {
    private final CartService cartService;
    private final UserService userService;
    private final LogManager logManager = LogManager.getLogManager();
    private final Logger logger = logManager.getLogger(Logger.GLOBAL_LOGGER_NAME);
    @PostMapping("/update")
    public CustomResponse updateCartItem(@RequestBody @Valid UpdateCartRequest updateCartRequest, @NotNull HttpServletRequest request) {
        try {
            String userEmail = userService.extractEmailFromRequest(request);
            boolean status = cartService.updateCartItem(updateCartRequest, userEmail);
            if(!status) {
                throw new Exception();
            }
            if(updateCartRequest.getQuantity() == 0) {
                logger.log(Level.INFO, "Product deleted Successfully in cart.");
                return new CustomResponse("Product deleted Successfully in cart.", HttpStatus.OK);
            }
            else {
                logger.log(Level.INFO, "Product updated Successfully in cart.");
                return new CustomResponse("Product updated Successfully in cart.", HttpStatus.OK);
            }
        }
        catch(Exception e) {
            logger.log(Level.WARNING, "Encountered a problem while updating the product from cart. - updateCartItem in CartController - " + e.getMessage());
            return new CustomResponse("Encountered a problem while updating the product from cart.", HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/view")
    public ResponseEntity<List<CartItem>> viewCart(@NotNull HttpServletRequest request) {
        try {
            String userEmail = userService.extractEmailFromRequest(request);
            Cart cart = cartService.getCartByUserEmail(userEmail);
            List<CartItem> items = cartService.getAllCartItemsByID(cart.getId());
            logger.log(Level.INFO, "Cart of user fetched Successfully.");
            return new ResponseEntity<>(items, HttpStatus.OK);
        } catch (Exception e) {
            logger.log(Level.WARNING, "Encountered a problem while fetching cart of user - viewCart in CartController " + e.getMessage());
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }
    }
    @PostMapping("/checkout")
    public CustomResponse checkout(@NotNull HttpServletRequest request) throws Exception {
        try {
            String userEmail = userService.extractEmailFromRequest(request);
            boolean status = cartService.deleteCartByUserEmail(userEmail);
            if(!status) {
                return new CustomResponse("Encountered a problem during checkout. -- Cart Empty", HttpStatus.OK);
            }
            logger.log(Level.INFO, "Checkout Successful.");
            return new CustomResponse("Checkout Successful.", HttpStatus.OK);
        } catch (Exception e) {
            logger.log(Level.WARNING, "Encountered a problem during checkout. - checkout in CartController " + e.getMessage());
            return new CustomResponse("Encountered a problem during checkout.", HttpStatus.BAD_REQUEST);
        }
    }
}
