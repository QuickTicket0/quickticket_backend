package com.quickticket.quickticket.domain.payment.credit.domain;

import com.quickticket.quickticket.domain.payment.seatPayment.domain.SeatPaymentIssue;
import com.quickticket.quickticket.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

/// Credit 원장에서 잔고 변동 각각을 나타내는 데이터
@Builder(access = PRIVATE)
@Getter
public class CreditTransaction {
    private Long id;
    private User user;
    /// 만약 transactionType이 좌석 결제와 관련된 것일 경우, null이 아닌 값이 됩니다
    private SeatPaymentIssue relatedSeatPaymentIssue;
    private TransactionType type;
    /// 기존에서부터 변동 금액
    private Long changeAmount;
    /// 새로 정해진 금액
    private Long balanceAfter;
    /// 변동이 발생한 시점입니다
    private LocalDateTime createdAt;

    /// 반드시 create로 생성된 객체가 DB에 할당되었을 상황에만 호출하세요
    public void assignId(Long id) {
        if (this.id != null) throw new IllegalStateException();

        this.id = id;
    }

    public static CreditTransaction create(
        TransactionType transactionType,
        Long changeAmount,
        Long balanceAfter,
        LocalDateTime createdAt
    ) {
        validateBalanceAfter(balanceAfter);

        return CreditTransaction.builder()
            .type(transactionType)
            .changeAmount(changeAmount)
            .balanceAfter(balanceAfter)
            .createdAt(createdAt)
            .build();
    }

    public static CreditTransaction recreate(
        Long id,
        TransactionType transactionType,
        Long changeAmount,
        Long balanceAfter,
        LocalDateTime createdAt
    ) {
        return CreditTransaction.builder()
            .id(id)
            .type(transactionType)
            .changeAmount(changeAmount)
            .balanceAfter(balanceAfter)
            .createdAt(createdAt)
            .build();
    }

    private static void validateBalanceAfter(Long balanceAfter) {
        if (
            balanceAfter == null
            || balanceAfter < 0
        ) {
            throw new IllegalArgumentException();
        }
    }
}