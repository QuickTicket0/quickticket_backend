package com.quickticket.quickticket.domain.seat.entity;

import com.quickticket.quickticket.domain.event.entity.Event;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "SEAT_AREA")
@Getter
@Setter
public class SeatArea {
    @EmbeddedId
    private SeatAreaId id;

    @MapsId("eventId")
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @NotNull
    @Column(nullable = false, length = 20)
    private String name;
}
