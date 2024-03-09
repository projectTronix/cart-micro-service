package com.mayank.cart.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Valid
public class UpdateCartRequest {
    @NotNull(message = "Product Id must not be Null.")
    @NotBlank(message = "Product Id must not be blank.")
    private String productId;
    @PositiveOrZero(message = "Quantity must be greater than or equal to zero.")
    @NotNull(message = "Quantity must not be null.")
    private Integer quantity;
    @PositiveOrZero(message = "QuantityAdd must be greater than or equal to zero.")
    private Integer quantityAdd;
}
