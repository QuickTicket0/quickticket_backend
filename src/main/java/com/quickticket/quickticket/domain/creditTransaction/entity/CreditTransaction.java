package com.quickticket.quickticket.domain.creditTransaction.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "CREDIT_TRANSACTION")
@Getter
@Setter
public class CreditTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(nullable = false)
    private Long creditTransactionId;

    @NotNull
    @Column(nullable = false)
    private TransactionType transactionType;

    @NotNull
    @Column(nullable = false)
    private Long changeAmount;

    @NotNull
    @Column(nullable = false)
    private Long balanceAfter;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
