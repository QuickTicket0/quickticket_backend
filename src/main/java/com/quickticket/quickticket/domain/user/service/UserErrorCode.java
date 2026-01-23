package com.quickticket.quickticket.domain.user.service;

import com.quickticket.quickticket.shared.exceptions.DomainErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum UserErrorCode implements DomainErrorCode {
    NOT_FOUND(HttpStatus.NOT_FOUND, "U001", "사용자가 존재하지 않습니다."),
    USERNAME_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "U002", "동일한 사용자명이 이미 존재합니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
