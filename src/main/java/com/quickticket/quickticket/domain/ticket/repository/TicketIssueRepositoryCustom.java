package com.quickticket.quickticket.domain.ticket.repository;

import com.quickticket.quickticket.domain.ticket.domain.Ticket;

public interface TicketIssueRepositoryCustom {
    Ticket getDomainById(Long ticketId);

    Ticket getDomainByWaitingNumber(Long waitingNumber, Long performanceId);

    Ticket saveDomain(Ticket domain);
}
