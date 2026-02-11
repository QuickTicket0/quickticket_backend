package com.quickticket.quickticket.domain.ticket.repository;

import com.quickticket.quickticket.domain.ticket.domain.Ticket;

import java.util.Optional;

public interface TicketBulkInsertQueueRepositoryCustom {
    Optional<Ticket> getDomainById(Long ticketId);

    Ticket saveDomain(Ticket domain);
}
