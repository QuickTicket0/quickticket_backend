package com.quickticket.quickticket.domain.payment.method.entity;

import com.quickticket.quickticket.domain.payment.method.domain.PaymentMethodType;
import com.quickticket.quickticket.shared.converters.AbstractOrdinalEnumConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class PaymentMethodTypeConverter extends AbstractOrdinalEnumConverter<PaymentMethodType> {
    public PaymentMethodTypeConverter() { super(PaymentMethodType.class); }
}
