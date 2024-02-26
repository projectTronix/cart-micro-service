package com.mayank.cart.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
    // The annotation's value can be replaced by any exception.
    // Use Throwable.class to handle **all** exceptions.
    // For this example I used the previously created exception.
    @ExceptionHandler(value = { ApiException.class })
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleApiRequestException(ApiException e) {

        // At this point, you can create the exception wrapper to create a
        // formatted JSON-response, but you could also just get the info
        // required from the exception and return that.
        ApiError error = new ApiError(
                e.getMessage(),
                HttpStatus.BAD_REQUEST,
                null,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(error, error.getStatus());
    }
}