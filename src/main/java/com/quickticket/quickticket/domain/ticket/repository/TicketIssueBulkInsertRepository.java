package com.quickticket.quickticket.domain.ticket.repository;

import com.quickticket.quickticket.domain.ticket.domain.Ticket;

public interface TicketIssueBulkInsertRepository {
    Ticket saveDomainForBulk(Ticket domain);
}
