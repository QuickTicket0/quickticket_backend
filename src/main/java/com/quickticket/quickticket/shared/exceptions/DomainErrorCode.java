package com.quickticket.quickticket.shared.exceptions;

public interface DomainErrorCode {
    String getCode();
    String getStatus();
    String getMessage();
}
