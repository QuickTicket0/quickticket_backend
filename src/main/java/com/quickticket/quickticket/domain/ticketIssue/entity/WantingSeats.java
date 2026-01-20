package com.quickticket.quickticket.domain.ticketIssue.entity;

import com.quickticket.quickticket.domain.seat.entity.Seat;
import com.quickticket.quickticket.domain.performance.entity.Performance;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "WANTING_SEATS")
@Getter
@Setter
public class WantingSeats {
    @EmbeddedId
    private WantingSeatsId id;

    @MapsId("ticketIssueId")
    @ManyToOne
    @JoinColumn(name = "ticket_issue_id")
    private TicketIssue ticketIssue;

    @MapsId("seatId")
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "seat_id", referencedColumnName = "seat_id"),
            @JoinColumn(name = "performance_id", referencedColumnName = "performance_id")
    })
    private Seat seat;
}

