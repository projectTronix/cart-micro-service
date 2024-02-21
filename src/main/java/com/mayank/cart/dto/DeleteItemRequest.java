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
public class DeleteItemRequest {
    @NotNull(message = "User Email must not be Null.")
    @NotBlank(message = "User Email must not be blank.")
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE,
            message = "Please provide a valid email.")
    private String userEmail;
    @NotNull(message = "Product Id must not be Null.")
    @NotBlank(message = "Product Id must not be blank.")
    private String productId;
    @Positive(message = "Quantity must be greater than zero.")
    @NotNull(message = "Quantity must not be null.")
    private Integer quantity;
}
