package com.quickticket.quickticket.domain.payment.method.domain;

import com.quickticket.quickticket.shared.converters.OrdinalEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TransactionType implements OrdinalEnum {
    SEAT_PAYMENT(0);

    public final int code;
}
