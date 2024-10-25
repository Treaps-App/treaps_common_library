package com.treaps.common.errorHandling.exception.commonCustomException;

import org.springframework.http.HttpStatus;

import com.treaps.common.errorHandling.exception.ApiException;

public class MessageProcessorNotFoundException extends ApiException{
    public MessageProcessorNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
