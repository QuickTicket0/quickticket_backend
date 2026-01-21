package com.quickticket.quickticket.domain.payment.credit.entity;

import com.quickticket.quickticket.domain.payment.credit.domain.TransactionType;
import com.quickticket.quickticket.shared.converters.AbstractOrdinalEnumConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TransactionTypeConverter extends AbstractOrdinalEnumConverter<TransactionType> {
    public TransactionTypeConverter() { super(TransactionType.class); }
}
