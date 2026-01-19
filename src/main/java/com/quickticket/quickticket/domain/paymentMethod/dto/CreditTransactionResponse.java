package com.quickticket.quickticket.domain.paymentMethod.dto;

import com.quickticket.quickticket.domain.paymentMethod.domain.TransactionType;
import lombok.Builder;

import java.time.LocalDateTime;

public class CreditTransactionResponse {
    @Builder
    public record Details(
        Long id,
        TransactionType transactionType,
        Long changeAmount,
        Long balanceAfter,
        LocalDateTime createdAt
    ) {}
}