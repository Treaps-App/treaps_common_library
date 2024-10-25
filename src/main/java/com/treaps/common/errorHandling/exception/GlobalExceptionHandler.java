package com.treaps.common.errorHandling.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Capture the user token from the request headers, handle cases where the token is absent
    private String getUserToken(HttpServletRequest request) {
        String noTokenCase = "No token provided";
        if( request == null ) return noTokenCase;
        String token = request.getHeader("Authorization");
        return (token != null && !token.isEmpty()) ? token : noTokenCase;
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(ApiException ex, HttpServletRequest request) {
        String userToken = getUserToken(request);
        logger.error("Error occurred for user token: {}, message: {}, stack trace: ", userToken, ex.getMessage(), ex);

        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), ex.getMessageForUser(), ex.getStatusCode().value());
        return new ResponseEntity<>(errorResponse, ex.getStatusCode());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex, HttpServletRequest request) {
        String userToken = getUserToken(request);
        logger.error("Error occurred for user token: {}, message: {}, stack trace: ", userToken, ex.getMessage(), ex);
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), "Internal Server Error", 500);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
