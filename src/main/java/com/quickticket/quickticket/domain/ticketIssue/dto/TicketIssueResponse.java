package com.quickticket.quickticket.domain.ticketIssue.dto;

public class TicketIssueResponse {
    public record Detail(
        String status,
        Integer waitingNumber,
        Long id,
        String createdAt
    ) {}
}
