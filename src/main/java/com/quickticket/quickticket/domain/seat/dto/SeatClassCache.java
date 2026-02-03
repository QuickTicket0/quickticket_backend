package com.quickticket.quickticket.domain.seat.dto;

public record SeatClassCache(
    Long id,
    Long eventId,
    String name,
    Long price
) {}
