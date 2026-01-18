package com.quickticket.quickticket.domain.wantSeat.entity;

import com.quickticket.quickticket.domain.ticketIssue.entity.TicketIssue;
import com.quickticket.quickticket.domain.seat.entity.Seat;
import com.quickticket.quickticket.domain.performance.entity.Performance;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

public class WantSeat {
    @Id
    @ManyToOne
    @NotNull
    @JoinColumn(name = "performance", nullable = false)
    private Performance performance;

    @Id
    @ManyToOne
    @NotNull
    @JoinColumn(name = "ticket_issue", nullable = false)
    private TicketIssue ticketIssue;

    @Id
    @ManyToOne
    @NotNull
    @JoinColumn(name = "seat", nullable = false)
    private Seat seat;


}

