package com.quickticket.quickticket.domain.payment.credit.domain;

import com.quickticket.quickticket.shared.converters.OrdinalEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TransactionType implements OrdinalEnum {
    SEAT_PAYMENT(0),
    CHARGE(1),       // 크레딧 충전
    REFUND(2);       // 예매 취소/환불

    public final int code;
}
