package com.mayank.cart.repository;

import com.mayank.cart.dto.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    List<CartItem> findAllByCartId(Integer cartId);
    @Query(value = "SELECT * FROM cart_items c where c.cart_id = :cart_id AND c.product_id = :product_id", nativeQuery = true)
    Optional<CartItem> findByProductIdAndCartId(@Param("cart_id") Integer cartId, @Param("product_id") String productId);
    @Modifying // It means it's not a select statement
    @Query(value = "DELETE FROM cart_items c where c.cart_id = :cart_id AND c.product_id = :product_id", nativeQuery = true)
    Integer deleteByProductIdAndCartId(@Param("cart_id") Integer cartId, @Param("product_id") String productId);
    @Modifying // It means it's not a select statement
    @Query(value = "UPDATE cart_items c SET c.quantity = :quantity WHERE c.cart_id = :cart_id AND c.product_id = :product_id", nativeQuery = true)
    void updateQuantityByProductIdAndCartId(@Param("quantity") Integer newQuantityInCart, @Param("product_id") String productId, @Param("cart_id") Integer id);
}
