package com.quickticket.quickticket.domain.seat.domain;

import com.quickticket.quickticket.domain.event.domain.Event;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SeatClass {
    private Long id;
    private Event event;
    private String name;
    private Long price;
}
