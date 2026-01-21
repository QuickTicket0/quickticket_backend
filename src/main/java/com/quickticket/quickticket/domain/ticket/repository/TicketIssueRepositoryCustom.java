package com.quickticket.quickticket.domain.ticket.repository;

import com.quickticket.quickticket.domain.ticket.dto.TicketResponse;

public interface TicketIssueRepositoryCustom {
    TicketResponse.Details getResponseDetailsById(Long id);
}
