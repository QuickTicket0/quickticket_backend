package com.quickticket.quickticket.domain.ticket.entity;

import com.quickticket.quickticket.domain.ticket.domain.Ticket;
import com.quickticket.quickticket.domain.ticket.domain.TicketStatus;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
@RedisHash("sync:bulk-insert-queue:ticket-issue")
public class TicketBulkInsertQueueEntity {
    @Id
    private Long ticketIssueId;

    private Long userId;
    private Long performanceId;
    private Long paymentMethodId;
    private TicketStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime canceledAt;
    private Long waitingNumber;
    private Integer personNumber;
    private List<Long> wantingSeatsId;

    public static TicketBulkInsertQueueEntity from(Ticket ticket) {
        return TicketBulkInsertQueueEntity.builder()
                .ticketIssueId(ticket.getId())
                .userId(ticket.getUser().getId())
                .performanceId(ticket.getPerformance().getId())
                .paymentMethodId(ticket.getPaymentMethod().getId())
                .status(ticket.getStatus())
                .createdAt(ticket.getCreatedAt())
                .canceledAt(ticket.getCanceledAt())
                .waitingNumber(ticket.getWaitingNumber())
                .personNumber(ticket.getPersonNumber())
                .wantingSeatsId(ticket.getWantingSeats().keySet().stream().toList())
                .build();
    }
}
