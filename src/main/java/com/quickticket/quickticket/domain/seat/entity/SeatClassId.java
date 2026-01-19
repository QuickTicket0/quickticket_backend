package com.quickticket.quickticket.domain.seat.entity;

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
public class SeatClassId implements Serializable {
    @Column(name = "seat_class_id")
    private Long seatClassId;

    @Column(name = "event_id")
    private Long eventId;

}
