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
    @JoinColumn(name = "user_id", columnDefinition = "INT UNSIGNED", nullable = false)
    private User user;

    @Id
    @Column(columnDefinition = "INT UNSIGNED", nullable = false)
    private Long seat;

    @Id
    @ManyToOne
    @JoinColumn(name = "performance_id", columnDefinition = "INT UNSIGNED", nullable = false)
    private Performance performance;

    @OneToOne
    @JoinColumn(name = "credit_transaction_id", columnDefinition = "INT UNSIGNED", nullable = false)
    private CreditTransaction creditTransaction;

    @NotNull
    private SeatPaymentIssueState state;
}
