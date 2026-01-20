package com.quickticket.quickticket.domain.ticket.entity;

import com.quickticket.quickticket.domain.seat.entity.SeatId;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class WantingSeatsId implements Serializable {
    private Long ticketIssueId;
    private SeatId seatId;
}
