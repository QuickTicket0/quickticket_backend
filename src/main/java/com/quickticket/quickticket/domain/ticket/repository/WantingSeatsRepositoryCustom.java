package com.quickticket.quickticket.domain.ticket.repository;

import com.quickticket.quickticket.domain.ticket.domain.Ticket;
import com.quickticket.quickticket.domain.ticket.entity.WantingSeatsEntity;
import com.quickticket.quickticket.domain.ticket.entity.WantingSeatsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface WantingSeatsRepositoryCustom {
    List<Long> getSeatIdsByTicketIssueId(Long ticketId);

    Ticket getNextTicketWantingTheSeatOrNull(Long fromNth, Long performanceId, Long seatId);
}
