package com.quickticket.quickticket.domain.seatPaymentIssue.entity;

import com.quickticket.quickticket.domain.creditTransaction.entity.CreditTransaction;
import com.quickticket.quickticket.domain.performance.entity.Performance;
import com.quickticket.quickticket.domain.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "SEAT_PAYMENT_ISSUE")
@IdClass(SeatPaymentIssueId.class)
@Getter
@Setter
public class SeatPaymentIssue {
    @Id
    @ManyToOne
    @NotNull
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Id
    @NotNull
    @Column(nullable = false)
    private Long seat;

    @Id
    @ManyToOne
    @NotNull
    @JoinColumn(name = "performance_id", nullable = false)
    private Performance performance;

    @OneToOne
    @NotNull
    @JoinColumn(name = "credit_transaction_id", nullable = false)
    private CreditTransaction creditTransaction;

    @NotNull
    @Column(nullable = false)
    private SeatPaymentIssueState state;
}
