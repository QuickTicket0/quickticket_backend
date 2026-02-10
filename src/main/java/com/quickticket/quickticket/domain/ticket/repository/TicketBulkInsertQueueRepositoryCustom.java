package com.quickticket.quickticket.domain.ticket.repository;

import com.quickticket.quickticket.domain.ticket.domain.Ticket;
import com.quickticket.quickticket.domain.ticket.entity.TicketBulkInsertQueueEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface TicketBulkInsertQueueRepositoryCustom {
    Ticket getDomainById(Long ticketId);

    Ticket saveDomain(Ticket domain);
}
