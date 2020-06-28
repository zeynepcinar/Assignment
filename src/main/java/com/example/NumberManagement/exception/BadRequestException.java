package com.example.NumberManagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public BadRequestException(final String message) {
        super(message);
    }

    public BadRequestException(final String message, final Exception cause) {
        super(message, cause);
    }
}