package com.quickticket.quickticket.domain.seat.domain;

import com.quickticket.quickticket.domain.event.domain.Event;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SeatArea {
    private Long id;
    private Event event;
    private String name;
}
