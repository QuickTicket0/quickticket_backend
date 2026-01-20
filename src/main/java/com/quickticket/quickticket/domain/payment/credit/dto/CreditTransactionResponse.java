package com.quickticket.quickticket.domain.payment.credit.dto;

import com.quickticket.quickticket.domain.payment.credit.domain.TransactionType;
import lombok.Builder;

import java.time.LocalDateTime;

public class CreditTransactionResponse {
    @Builder
    public record Details(
        Long id,
        TransactionType transactionType,
        Long changeAmount,
        Long balanceAfter,
        LocalDateTime createdAt,
        String summary  // 크레딧내역(myCredit.html)의 사용내역(20260120_조민지추가)
    ) {}
}