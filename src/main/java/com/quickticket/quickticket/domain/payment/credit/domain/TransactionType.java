package com.quickticket.quickticket.domain.payment.credit.domain;

import com.quickticket.quickticket.shared.converters.OrdinalEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TransactionType implements OrdinalEnum {
    /// 좌석 결제
    SEAT_PAYMENT(0),
    /// 예매 취소/환불
    REFUND(1),
    /// 크레딧 충전
    CHARGE(2);

    private final int code;
}
