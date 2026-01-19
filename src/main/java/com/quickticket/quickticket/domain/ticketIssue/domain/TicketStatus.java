package com.quickticket.quickticket.domain.ticketIssue.domain;

import com.quickticket.quickticket.shared.converters.OrdinalEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TicketStatus implements OrdinalEnum {
    WAITING(0);

    public final int code;
}
