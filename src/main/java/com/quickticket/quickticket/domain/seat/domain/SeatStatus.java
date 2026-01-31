package com.quickticket.quickticket.domain.seat.domain;

import com.quickticket.quickticket.shared.converters.OrdinalEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SeatStatus implements OrdinalEnum {
    TICKET_ALLOCATED(0),
    AVAILABLE(1);

    private final int code;
}
