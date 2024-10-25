package com.treaps.common.errorHandling.exception;

import lombok.Getter;

import java.time.Instant;

@Getter
public class ErrorResponse {
    private final String error;
    private final String messageToUser;
    private final int statusCode;
    private final Instant timestamp;

    public ErrorResponse(String error, String messageToUser, int statusCode) {
        this.error = error;
        this.messageToUser = messageToUser;
        this.statusCode = statusCode;
        this.timestamp = Instant.now();
    }
    public ErrorResponse(String error, int statusCode) {
        this.error = error;
        this.messageToUser = "Something went wrong";
        this.statusCode = statusCode;
        this.timestamp = Instant.now();
    }

}
