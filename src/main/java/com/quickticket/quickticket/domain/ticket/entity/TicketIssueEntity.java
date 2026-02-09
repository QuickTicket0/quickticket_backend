package com.quickticket.quickticket.domain.ticket.entity;

import com.quickticket.quickticket.domain.payment.method.entity.PaymentMethodEntity;
import com.quickticket.quickticket.domain.performance.entity.PerformanceEntity;
import com.quickticket.quickticket.domain.ticket.domain.TicketStatus;
import com.quickticket.quickticket.domain.user.entity.UserEntity;
import com.quickticket.quickticket.shared.generators.RedisSequenceId;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "TICKET_ISSUE")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TicketIssueEntity {
    @Id
    @RedisSequenceId(key = "sync:sequence-id:ticket-issue")
    @NotNull
    @Column(name = "ticket_issue_id", nullable = false)
    private Long ticketIssueId;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "performance_id", nullable = false)
    private PerformanceEntity performance;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "payment_method_id", nullable = false)
    private PaymentMethodEntity paymentMethod;

    @NotNull
    @Column(name = "ticket_status", nullable = false)
    private TicketStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime canceledAt;

    private Long waitingNumber;

    @NotNull
    @Column(nullable = false)
    private Integer personNumber;

}
