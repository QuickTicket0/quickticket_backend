package com.quickticket.quickticket.domain.ticket.entity;

import com.quickticket.quickticket.domain.seat.entity.SeatEntity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "WANTING_SEATS")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WantingSeatsEntity {
    @EmbeddedId
    private WantingSeatsId id;

    @MapsId("ticketIssueId")
    @ManyToOne
    @JoinColumn(name = "ticket_issue_id")
    private TicketIssueEntity ticketIssue;

    @MapsId("seatId")
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "seat_id", referencedColumnName = "seat_id"),
            @JoinColumn(name = "performance_id", referencedColumnName = "performance_id")
    })
    private SeatEntity seat;
}
