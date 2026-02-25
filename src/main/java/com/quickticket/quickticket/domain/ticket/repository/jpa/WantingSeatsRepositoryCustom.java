package com.quickticket.quickticket.domain.ticket.repository.jpa;

import com.quickticket.quickticket.domain.ticket.domain.Ticket;

import java.util.List;

public interface WantingSeatsRepositoryCustom {
    List<Long> getSeatIdsByTicketIssueId(Long ticketId);

    Ticket getNextTicketWantingTheSeatOrNull(Long fromNth, Long performanceId, Long seatId);
}
