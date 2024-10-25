package com.treaps.common.errorHandling.exception;

// Base exception class
import org.springframework.http.HttpStatus;

import lombok.Getter;

import java.time.Instant;

@Getter
public class ApiException extends RuntimeException {
    private final HttpStatus statusCode;
    private final Instant timestamp;
    private final String messageForUser;

    public ApiException(String errorMessage) {
        super(errorMessage);
        this.statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        this.timestamp = Instant.now();
        this.messageForUser = "Something went wrong";
    }
    public ApiException(String errorMessage, HttpStatus statusCode) {
        super(errorMessage);
        this.statusCode = statusCode;
        this.timestamp = Instant.now();
        this.messageForUser = "Something went wrong";
    }
    public ApiException(String errorMessage, HttpStatus statusCode, String messageForUser) {
        super(errorMessage);
        this.statusCode = statusCode;
        this.timestamp = Instant.now();
        this.messageForUser = messageForUser;
    }

}
