package com.quickticket.quickticket.domain.seat.domain;

import com.quickticket.quickticket.domain.event.domain.Event;
import com.quickticket.quickticket.shared.annotations.Default;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor(onConstructor_ = {@Default})
public class SeatArea {
    private Long id;
    private Event event;
    private String name;
}
