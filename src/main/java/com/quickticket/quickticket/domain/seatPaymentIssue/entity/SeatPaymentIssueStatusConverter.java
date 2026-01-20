package com.quickticket.quickticket.domain.seatPaymentIssue.entity;

import com.quickticket.quickticket.shared.converters.AbstractOrdinalEnumConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class SeatPaymentIssueStatusConverter extends AbstractOrdinalEnumConverter<SeatPaymentIssueStatus> {
    public SeatPaymentIssueStatusConverter() { super(SeatPaymentIssueStatus.class); }
}
