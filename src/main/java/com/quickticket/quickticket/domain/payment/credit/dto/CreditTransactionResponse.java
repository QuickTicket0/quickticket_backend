package com.quickticket.quickticket.domain.payment.credit.dto;

import com.quickticket.quickticket.domain.payment.credit.domain.TransactionType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class CreditTransactionResponse {
    // 화면 상단 현재 총 잔액
    private Long totalBalance;
    // 화면 하단 거래 내역 리스트
    private List<Details> transactionList;

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