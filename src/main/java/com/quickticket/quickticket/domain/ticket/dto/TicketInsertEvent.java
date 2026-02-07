package com.quickticket.quickticket.domain.ticket.dto;

import com.quickticket.quickticket.domain.ticket.domain.Ticket;
import com.quickticket.quickticket.domain.ticket.domain.TicketStatus;
import lombok.AccessLevel;
import lombok.Builder;

import java.time.LocalDateTime;

public record TicketInsertEvent(
    TicketInsertDto dto,
    LocalDateTime createdAt
) {
    public static TicketInsertEvent from(Ticket ticket) {
        return new TicketInsertEvent(
            TicketInsertDto.from(ticket),
            LocalDateTime.now()
        );
    }

    @Builder(access = AccessLevel.PRIVATE)
    private record TicketInsertDto(
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
        private static TicketInsertDto from(Ticket ticket) {
            return TicketInsertDto.builder()
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
}
