package com.quickticket.quickticket.domain.ticket.service;

import com.quickticket.quickticket.shared.exceptions.DomainErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum TicketErrorCode implements DomainErrorCode {
    NOT_FOUND(HttpStatus.NOT_FOUND, "T001", "예매를 찾을 수 없습니다."),
    USER_NOT_EQUAL(HttpStatus.BAD_REQUEST, "T002", "사용자 본인의 예매만 취소할 수 있습니다."),
    CANCELED_ALREADY(HttpStatus.BAD_REQUEST, "T003", "이미 취소된 예매입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
