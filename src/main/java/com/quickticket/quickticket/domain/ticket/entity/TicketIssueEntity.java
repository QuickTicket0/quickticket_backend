package com.quickticket.quickticket.domain.ticket.entity;

import com.quickticket.quickticket.domain.payment.method.entity.PaymentMethodEntity;
import com.quickticket.quickticket.domain.performance.entity.PerformanceEntity;
import com.quickticket.quickticket.domain.user.entity.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "TICKET_ISSUE")
@Getter
@Setter
public class TicketIssueEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Column(nullable = false)
    private Long waitingNumber;

    @NotNull
    @Column(nullable = false)
    private Integer personNumber;

}
