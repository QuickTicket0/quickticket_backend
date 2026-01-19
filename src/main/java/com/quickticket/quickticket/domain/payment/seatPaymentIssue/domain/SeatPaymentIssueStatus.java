package com.quickticket.quickticket.domain.payment.seatPaymentIssue.domain;

import com.quickticket.quickticket.shared.converters.OrdinalEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SeatPaymentIssueStatus implements OrdinalEnum {
    PAID(0);

    public final int code;
}
