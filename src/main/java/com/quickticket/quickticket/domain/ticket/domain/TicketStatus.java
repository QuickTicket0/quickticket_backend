package com.quickticket.quickticket.domain.ticket.domain;

import com.quickticket.quickticket.shared.converters.OrdinalEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TicketStatus implements OrdinalEnum {
    WAITING(0),
    PRESET(1);

    public final int code;
}
