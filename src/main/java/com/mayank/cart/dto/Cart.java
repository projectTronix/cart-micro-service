package com.mayank.cart.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Valid
@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Integer id;
    @Column(nullable = false)
    @NotNull(message = "User Email must not be Null.")
    @NotBlank(message = "User Email must not be blank.")
    private String userEmail;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cart")
    @JsonManagedReference
    private List<CartItem> items;
}
