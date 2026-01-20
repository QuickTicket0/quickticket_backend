package com.quickticket.quickticket.domain.ticketIssue.entity;

import com.quickticket.quickticket.domain.paymentMethod.entity.PaymentMethod;
import com.quickticket.quickticket.domain.performance.entity.Performance;
import com.quickticket.quickticket.domain.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "TICKET_ISSUE")
@Getter
@Setter
public class TicketIssue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name = "ticket_issue_id", nullable = false)
    private Long ticketIssueId;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "performance_id", nullable = false)
    private Performance performance;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "payment_method_id", nullable = false)
    private PaymentMethod paymentMethod;

    @NotNull
    @Column(nullable = false)
    private Long waitingNumber;

    @NotNull
    @Column(nullable = false)
    private Integer personNumber;

}
