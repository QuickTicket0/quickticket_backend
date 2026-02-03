package com.quickticket.quickticket.domain.seat.dto;

import lombok.Builder;

@Builder
public record SeatAreaCache(
    Long id,
    Long eventId,
    String name
) {}
