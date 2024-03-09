package com.mayank.cart.controller;

import com.mayank.cart.config.UserService;
import com.mayank.cart.dto.Cart;
import com.mayank.cart.dto.CartItem;
import com.mayank.cart.dto.CustomResponse;
import com.mayank.cart.dto.UpdateCartRequest;
import com.mayank.cart.service.CartService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CartControllerTest {
    @Autowired
    private CartController cartController;
    @MockBean
    private CartService cartService;
    @MockBean
    private UserService userService;
    @Test
    public void testViewCart() throws Exception {
        Cart cart = Cart.builder().id(2).build();
        when(userService.extractEmailFromRequest(any())).thenReturn("mayank@gg.com");
        when(cartService.getCartByUserEmail(any())).thenReturn(cart);
        List<CartItem> cartItems = List.of(CartItem.builder().id(2).build());
        when(cartService.getAllCartItemsByID(cart.getId())).thenReturn(cartItems);
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        ResponseEntity<List<CartItem>> items = cartController.viewCart(mockHttpServletRequest);
        Assertions.assertEquals(HttpStatus.OK, items.getStatusCode());
        Assertions.assertEquals(2, Objects.requireNonNull(items.getBody()).get(0).getId());
    }
    @Test
    public void testUpdateCartItem() throws Exception {
        when(userService.extractEmailFromRequest(any())).thenReturn("mayank@gg.com");
        when(cartService.updateCartItem(any(), any())).thenReturn(true);
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        CustomResponse customResponse = cartController.updateCartItem(UpdateCartRequest.builder().quantity(2).productId("heya").build(),mockHttpServletRequest);
        Assertions.assertEquals(HttpStatus.OK, customResponse.getStatus());
    }
    @Test
    public void testUpdateCartItemDelete() throws Exception {
        when(userService.extractEmailFromRequest(any())).thenReturn("mayank@gg.com");
        when(cartService.updateCartItem(any(), any())).thenReturn(true);
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        CustomResponse customResponse = cartController.updateCartItem(UpdateCartRequest.builder().quantity(0).productId("heya").build(),mockHttpServletRequest);
        Assertions.assertEquals(HttpStatus.OK, customResponse.getStatus());
    }
    @Test
    public void testUpdateCartItemFalse() throws Exception {
        when(userService.extractEmailFromRequest(any())).thenReturn("mayank@gg.com");
        when(cartService.updateCartItem(any(), any())).thenReturn(false);
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        CustomResponse customResponse = cartController.updateCartItem(UpdateCartRequest.builder().quantity(0).productId("heya").build(),mockHttpServletRequest);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, customResponse.getStatus());
    }
    @Test
    public void testCheckout() throws Exception {
        when(userService.extractEmailFromRequest(any())).thenReturn("mayank@gg.com");
        when(cartService.deleteCartByUserEmail(any())).thenReturn(true);
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        CustomResponse customResponse = cartController.checkout(mockHttpServletRequest);
        Assertions.assertEquals(HttpStatus.OK, customResponse.getStatus());
    }
    @Test
    public void testCheckoutCartEmpty() throws Exception {
        when(userService.extractEmailFromRequest(any())).thenReturn("mayank@gg.com");
        when(cartService.deleteCartByUserEmail(any())).thenReturn(false);
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        CustomResponse customResponse = cartController.checkout(mockHttpServletRequest);
        Assertions.assertEquals(HttpStatus.OK, customResponse.getStatus());
    }}
