package com.quickticket.quickticket.domain.payment.credit.entity;

import com.quickticket.quickticket.domain.payment.credit.domain.TransactionType;
import com.quickticket.quickticket.domain.payment.seatPayment.entity.SeatPaymentIssueEntity;
import com.quickticket.quickticket.domain.user.entity.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "CREDIT_TRANSACTION")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreditTransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(nullable = false)
    private Long creditTransactionId;

    @ManyToOne
    @NotNull
    @JoinColumn(nullable = false)
    private UserEntity user;

    @OneToOne
    @NotNull
    @JoinColumn(nullable = false)
    private SeatPaymentIssueEntity payment;

    @NotNull
    @Column(nullable = false)
    private TransactionType transactionType;

    @OneToOne
    @JoinColumn
    private SeatPaymentIssueEntity relatedSeatPaymentIssue;

    @NotNull
    @Column(nullable = false)
    private Long changeAmount;

    @NotNull
    @Column(nullable = false)
    private Long balanceAfter;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
