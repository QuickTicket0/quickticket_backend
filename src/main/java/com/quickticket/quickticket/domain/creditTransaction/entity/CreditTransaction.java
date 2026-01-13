package com.quickticket.quickticket.domain.creditTransaction.entity;

import jakarta.persistence.*;
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
    @Column(columnDefinition = "INT UNSIGNED", nullable = false)
    private Long creditTransactionId;

    @Column(columnDefinition = "TINYINT UNSIGNED", nullable = false)
    private TransactionType transactionType;

    @Column(nullable = false)
    private int changeAmount;

    @Column(columnDefinition = "INT UNSIGNED", nullable = false)
    private long balanceAfter;

    @Column(columnDefinition = "TIMESTAMP")
    @CreationTimestamp
    private LocalDateTime createdAt;
}
