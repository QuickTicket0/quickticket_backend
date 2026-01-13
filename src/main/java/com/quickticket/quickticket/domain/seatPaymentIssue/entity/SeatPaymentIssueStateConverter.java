package com.quickticket.quickticket.domain.seatPaymentIssue.entity;

import com.quickticket.quickticket.shared.converters.AbstractOrdinalEnumConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class SeatPaymentIssueStateConverter extends AbstractOrdinalEnumConverter<SeatPaymentIssueState> {
    public SeatPaymentIssueStateConverter() { super(SeatPaymentIssueState.class); }
}
