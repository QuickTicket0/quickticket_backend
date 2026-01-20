package com.quickticket.quickticket.domain.seat.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SeatId implements Serializable {
    @Column(name = "seat_id")
    private Long seatId;

    @Column(name = "performance_id")
    private Long performanceId;

}