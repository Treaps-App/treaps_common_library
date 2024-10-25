package com.treaps.common.errorHandling.exception.commonCustomException;

import org.springframework.http.HttpStatus;

import com.treaps.common.errorHandling.exception.ApiException;

public class BadRequestException extends ApiException {
    public BadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST, "Bad Request");
    }
}
