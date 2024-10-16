package com.treaps.common.errorHandling.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ErrorDetails {
    private LocalDateTime timestamp;
    private String message;
    private String details;

    private HttpStatus status;

    public ErrorDetails(HttpStatus status, String message, String details) {
        this.timestamp = LocalDateTime.now();
        this.message = message;
        this.details = details;
        this.status = status;
    }

    // Getters and setters
}
