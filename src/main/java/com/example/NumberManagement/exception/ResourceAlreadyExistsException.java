package com.example.NumberManagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ResourceAlreadyExistsException extends RuntimeException {

    public ResourceAlreadyExistsException(String errorMessage) {
        super(errorMessage);
    }
}
