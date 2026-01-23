package com.quickticket.quickticket.shared.exceptions;

import org.springframework.http.HttpStatus;

public interface DomainErrorCode {
    String getCode();
    HttpStatus getStatus();
    String getMessage();
}
