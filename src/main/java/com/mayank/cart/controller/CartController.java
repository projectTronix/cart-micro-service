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

    @PostMapping("/add")
    public CustomResponse addToCart(@RequestBody @Valid AddToCartRequest request) {
        try {
            boolean status = cartService.addToCart(request);
            if (!status) throw new Exception();
            logger.log(Level.INFO, "Product added Successfully.");
            return new CustomResponse("Product added Successfully.", HttpStatus.OK);
        } catch (Exception e) {
            logger.log(Level.WARNING, "Encountered a problem while adding product to cart -- addToProduct in CartController. - " + e.getMessage());
            return new CustomResponse("Encountered a problem while adding product to cart.", HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/view")
    public ResponseEntity<List<CartItem>> viewCart(@RequestBody @Valid ViewCartRequest request) {
        try {
            String userEmail = request.getUserEmail();
            Cart cart = cartService.getCartByUserEmail(userEmail);
            List<CartItem> items = cartService.getAllCartItemsByID(cart.getId());
            logger.log(Level.INFO, "Cart of User fetched Successfully.");
            return new ResponseEntity<>(items, HttpStatus.OK);
        } catch (Exception e) {
            logger.log(Level.WARNING, "Encountered a problem while fetching cart of user - viewCart in CartController :" + e.getMessage());
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/delete-item")
    public CustomResponse deleteItem(@RequestBody @Valid DeleteItemRequest request) {
        try {
            String productId = request.getProductId();
            String userEmail = request.getUserEmail();
            boolean status = cartService.deleteItem(request);
            if(!status) {
                throw new Exception();
            }
            logger.log(Level.INFO, "Product deleted Successfully.");
            return new CustomResponse("Product deleted successfully.", HttpStatus.OK);
        }
        catch(Exception e) {
            logger.log(Level.WARNING, "Encountered a problem while deleting the product from cart. - deleteItem in CartController - " + e.getMessage());
            return new CustomResponse("Encountered a problem while deleting the product from cart.", HttpStatus.BAD_REQUEST);
        }
    }
}
