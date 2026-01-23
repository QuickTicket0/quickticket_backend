package com.quickticket.quickticket.domain.user.service;

import com.quickticket.quickticket.shared.exceptions.DomainErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserErrorCode implements DomainErrorCode {
    NOT_FOUND(404, "U001", "사용자가 존재하지 않습니다."),
    USERNAME_ALREADY_EXISTS(400, "U002", "동일한 사용자명이 이미 존재합니다.");

    private final int status;
    private final String code;
    private final String message;
}
