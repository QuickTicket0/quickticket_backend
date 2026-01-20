package com.quickticket.quickticket.domain.creditTransaction.entity;

import com.quickticket.quickticket.shared.converters.AbstractOrdinalEnumConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TransactionTypeConverter extends AbstractOrdinalEnumConverter<TransactionType> {
    public TransactionTypeConverter() { super(TransactionType.class); }
}
