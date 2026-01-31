package com.quickticket.quickticket.domain.performance.service;

import com.quickticket.quickticket.shared.exceptions.DomainErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum PerformanceErrorCode implements DomainErrorCode {
    NOT_FOUND(HttpStatus.NOT_FOUND, "P001", "공연이 존재하지 않습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
