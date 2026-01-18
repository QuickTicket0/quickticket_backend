package com.quickticket.quickticket.domain.seatClass.entity;

import com.quickticket.quickticket.domain.event.entity.Event;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "SEAT_CLASS")
@IdClass(SeatClassId.class)
@Getter
@Setter

public class SeatClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(nullable = false)
    private SeatClassId seatclassId;

    @Id
    @ManyToOne
    @NotNull
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @NotNull
    @Column(nullable = false)
    private String name;

    @NotNull
    @Column(nullable = false)
    private Long price;

}
