package com.quickticket.quickticket.domain.payment.seatPayment.domain;

import com.quickticket.quickticket.shared.converters.OrdinalEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SeatPaymentIssueStatus implements OrdinalEnum {
    FAILED(0),
    SUCCESS(1);

    private final int code;
}
