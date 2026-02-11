package com.quickticket.quickticket.domain.ticket.repository;

import com.quickticket.quickticket.domain.ticket.domain.Ticket;
import com.quickticket.quickticket.domain.ticket.dto.TicketResponse;

import java.util.List;
import java.util.Optional;

public interface TicketIssueRepositoryCustom {
    Optional<Ticket> getDomainById(Long ticketId);

    boolean existsById(Long ticketId);

    Ticket saveDomain(Ticket domain);

    Ticket saveDomainToBulkQueue(Ticket domain);

    List<TicketResponse.ListItem> getListItemsByUserId(Long userId);

    Optional<TicketResponse.Details> getDetailsById(Long ticketId);

}
