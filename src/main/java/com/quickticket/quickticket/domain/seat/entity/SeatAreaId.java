package com.quickticket.quickticket.domain.seat.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class SeatAreaId implements Serializable {
    @Column(name = "seat_area_id")
    private Long seatAreaId;

    @Column(name = "event_id")
    private Long eventId;
}
