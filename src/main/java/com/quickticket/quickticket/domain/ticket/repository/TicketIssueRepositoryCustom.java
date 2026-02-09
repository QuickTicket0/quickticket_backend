package com.quickticket.quickticket.domain.ticket.repository;

import com.quickticket.quickticket.domain.ticket.domain.Ticket;
import com.quickticket.quickticket.domain.ticket.dto.TicketResponse;

import java.util.List;

public interface TicketIssueRepositoryCustom {
    Ticket getDomainById(Long ticketId);

    Ticket saveDomain(Ticket domain);

    List<TicketResponse.ListItem> getListItemsByUserId(Long userId);

    TicketResponse.Details getDetailsById(Long ticketId);
}
