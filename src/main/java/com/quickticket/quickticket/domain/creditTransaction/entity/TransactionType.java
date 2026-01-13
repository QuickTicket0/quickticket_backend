package com.quickticket.quickticket.domain.creditTransaction.entity;

import com.quickticket.quickticket.shared.converters.OrdinalEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TransactionType implements OrdinalEnum {
    TICKET_PAYMENT(0),
    REFUND(1),
    CREDIT_ADD(2);

    public final int code;
}
