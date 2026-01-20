package com.quickticket.quickticket.domain.seatPaymentIssue.entity;

import com.quickticket.quickticket.shared.converters.OrdinalEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SeatPaymentIssueStatus implements OrdinalEnum {
    PAYMENT_FAILED(0),
    PAYMENT_SUCCESS(1);

    private final int code;
}
