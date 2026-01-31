package com.quickticket.quickticket.domain.seat.entity;

import com.quickticket.quickticket.domain.event.entity.EventEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Entity
@Table(name = "SEAT_CLASS")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SeatClassEntity {
    @EmbeddedId
    private SeatClassId id;

    @MapsId("eventId")
    @ManyToOne
    @JoinColumn(name = "event_id")
    private EventEntity event;

    @NotNull
    @Column(nullable = false, length = 20)
    private String name;

    @NotNull
    @Column(nullable = false)
    private Long price;
}
