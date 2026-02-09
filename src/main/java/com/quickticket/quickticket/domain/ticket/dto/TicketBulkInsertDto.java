package com.quickticket.quickticket.domain.ticket.dto;

import com.quickticket.quickticket.domain.ticket.domain.Ticket;
import com.quickticket.quickticket.domain.ticket.domain.TicketStatus;
import lombok.AccessLevel;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder(access = AccessLevel.PRIVATE)
public record TicketBulkInsertDto(
        Long ticketIssueId,
        Long userId,
        Long performanceId,
        Long paymentMethodId,
        TicketStatus status,
        LocalDateTime createdAt,
        LocalDateTime canceledAt,
        Long waitingNumber,
        Integer personNumber
) {
    public static TicketBulkInsertDto from(Ticket ticket) {
        return TicketBulkInsertDto.builder()
                .ticketIssueId(ticket.getId())
                .userId(ticket.getUser().getId())
                .performanceId(ticket.getPerformance().getId())
                .paymentMethodId(ticket.getPaymentMethod().getId())
                .status(ticket.getStatus())
                .createdAt(ticket.getCreatedAt())
                .canceledAt(ticket.getCanceledAt())
                .waitingNumber(ticket.getWaitingNumber())
                .personNumber(ticket.getPersonNumber())
                .build();
    }
}