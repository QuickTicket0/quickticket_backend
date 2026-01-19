package com.quickticket.quickticket.domain.paymentMethod.domain;

import com.quickticket.quickticket.shared.converters.OrdinalEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PaymentMethodType implements OrdinalEnum {
    CARD(0),
    CREDIT(1);

    public final int code;
}
