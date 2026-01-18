package com.quickticket.quickticket.domain.seatArea.entity;

import com.quickticket.quickticket.domain.event.entity.Event;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "SEAT_AREA")
@IdClass(SeatAreaId.class)
@Getter
@Setter

public class SeatArea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(nullable = false)
    private SeatAreaId seatareaId;

    @Id
    @ManyToOne
    @NotNull
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @NotNull
    @Column(nullable = false)
    private String name;

}
