package com.quickticket.quickticket.domain.seat.dto;

import com.quickticket.quickticket.domain.seat.domain.SeatStatus;
import lombok.Builder;

@Builder
public record SeatCache (
    Long id,
    String name,
    Long performanceId,
    Long seatClassId,
    Long seatAreaId,
    SeatStatus status,
    Long currentWaitingNumber
) {}
