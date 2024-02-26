package com.mayank.cart.exception;

import lombok.Getter;

public class ApiException extends RuntimeException {

    // Not really needed here, as Throwable.java has a message too
    // I added it for better readability
    @Getter
    private String message;

    public ApiException() {
        super();
    }

    public ApiException(String message) {
        this.message = message;
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
