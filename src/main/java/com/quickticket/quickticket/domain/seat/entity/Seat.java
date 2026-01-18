package com.quickticket.quickticket.domain.seat.entity;

import com.quickticket.quickticket.domain.performance.entity.Performance;
import com.quickticket.quickticket.domain.event.entity.Event;
import com.quickticket.quickticket.domain.seatClass.entity.SeatClass;
import com.quickticket.quickticket.domain.seatArea.entity.SeatArea;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "SEAT")
@IdClass(SeatId.class)
@Getter
@Setter
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(nullable = false)
    private Long seatId;

    @Id
    @ManyToOne
    @NotNull
    @JoinColumn(name = "event_id", nullable = false)
    private Event event1;

    @Id
    @ManyToOne
    @NotNull
    @JoinColumn(name = "event_id2", nullable = false)
    private Event event2;

    @Id
    @ManyToOne
    @NotNull
    @JoinColumn(name = "performance", nullable = false)
    private Performance performance;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "seat_class_id", nullable = false)
    private SeatClass seatclass;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "seat_area_id", nullable = false)
    private SeatArea seatarea;

    @NotNull
    @Column(nullable = false)
    private Integer current_waiting_number;

}
