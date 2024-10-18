package com.treaps.common.errorHandling.exception;

public class MessageProcessorNotFoundException extends RuntimeException {
    public MessageProcessorNotFoundException(String message) {
        super(message);
    }
}
