package com.quickticket.quickticket.domain.ticket.repository;

import com.quickticket.quickticket.domain.ticket.domain.Ticket;

public interface TicketBulkInsertQueueRepositoryCustom {
    Ticket getDomainById(Long ticketId);

    Ticket saveDomain(Ticket domain);
}
