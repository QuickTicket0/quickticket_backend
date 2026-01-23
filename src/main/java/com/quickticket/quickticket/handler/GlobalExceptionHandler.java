package com.quickticket.quickticket.handler;

import com.quickticket.quickticket.shared.exceptions.DomainException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(DomainException.class)
    public ResponseEntity<String> handle(DomainException e) {
        var errorCode = e.getErrorCode();

        return ResponseEntity
                .status(errorCode.getStatus())
                .body(e.getMessage());
    }
}
