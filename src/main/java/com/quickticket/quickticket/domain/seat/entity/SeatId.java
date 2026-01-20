package com.quickticket.quickticket.domain.seat.entity;

import com.quickticket.quickticket.domain.event.entity.Event;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
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