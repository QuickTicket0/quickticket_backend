package com.quickticket.quickticket.domain.ticket.repository;

import com.quickticket.quickticket.domain.ticket.domain.Ticket;
import com.quickticket.quickticket.domain.ticket.entity.TicketIssueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface TicketIssueRepository extends JpaRepository<TicketIssueEntity, Long> {
    Long getLastWaitingNumberOfPerformance(Long performanceId);

    Ticket getDomainById(Long ticketId);

    Ticket getDomainByWaitingNumber(Long waitingNumber, Long performanceId);

    Ticket saveDomain(Ticket domain);
}
