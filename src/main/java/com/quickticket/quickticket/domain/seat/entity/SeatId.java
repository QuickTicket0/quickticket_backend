package com.quickticket.quickticket.domain.seat.entity;

import com.quickticket.quickticket.domain.event.entity.Event;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@EqualsAndHashCode
public class SeatId implements Serializable {
    private Event eventId;
    private Event eventId2;
    private Long performance;
}