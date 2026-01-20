package com.quickticket.quickticket.domain.payment.credit.dto;

import com.quickticket.quickticket.domain.payment.credit.domain.TransactionType;
import lombok.Builder;

import java.time.LocalDateTime;

public class CreditTransactionResponse {
    /// 사용자가 Credit 변동내역 전체를 리스트로 조회할때
    @Builder
    public record Details(
        Long id,
        TransactionType transactionType,
        Long changeAmount,
        Long balanceAfter,
        LocalDateTime createdAt,
        String summary  // 크레딧내역(myCredit.html)의 사용내역
    ) {}
}