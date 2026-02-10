package com.quickticket.quickticket.domain.ticket.entity;

import com.quickticket.quickticket.domain.ticket.domain.Ticket;
import com.quickticket.quickticket.domain.ticket.domain.TicketStatus;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

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

    @Indexed
    private Long userId;

    private Long performanceId;
    private Long paymentMethodId;
    private TicketStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime canceledAt;
    private Long waitingNumber;
    private Integer personNumber;
    private List<Long> wantingSeatsId;
}
