package com.mayank.cart.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Valid
@Table(name = "cartItems")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    @NotNull(message = "Product Id must not be Null.")
    @NotBlank(message = "Product Id must not be blank.")
    private String productId;
    @Positive(message = "Quantity must be greater than zero.")
    @NotNull(message = "Quantity must not be null.")
    @Column(nullable = false)
    private Integer quantity;
    @ManyToOne(cascade = CascadeType.ALL)
    //Adding the name
    @JoinColumn(name = "cart_id", referencedColumnName="cart_id")
    @JsonBackReference
    private Cart cart;
}
