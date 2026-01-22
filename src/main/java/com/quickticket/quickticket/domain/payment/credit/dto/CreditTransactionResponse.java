package com.quickticket.quickticket.domain.payment.credit.dto;

import com.quickticket.quickticket.domain.payment.credit.domain.TransactionType;
import com.quickticket.quickticket.domain.payment.credit.entity.CreditTransactionEntity;
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
        /// 크레딧내역(myCredit.html)의 사용내역
        String summary
    ) {
        public static Details from(CreditTransactionEntity transaction) {
            String summary = null;

            switch (transaction.getTransactionType()) {
                case SEAT_PAYMENT ->
                    summary = "'" + transaction
                            .getRelatedSeatPaymentIssue()
                            .getTicketIssue()
                            .getPerformance()
                            .getEvent()
                            .getName() + "' 콘서트 구매";
                case CHARGE ->
                    summary = "크레딧 충전";
                case REFUND ->
                    summary = "크레딧 환불";
            }

            return Details.builder()
                    .id(transaction.getCreditTransactionId())
                    .transactionType(transaction.getTransactionType())
                    .changeAmount(transaction.getChangeAmount())
                    .balanceAfter(transaction.getBalanceAfter())
                    .createdAt(transaction.getCreatedAt())
                    .summary(summary)
                    .build();
        }
    }
}