package com.quickticket.quickticket.domain.payment.credit.domain;

import com.quickticket.quickticket.domain.payment.seatPayment.domain.SeatPaymentIssue;
import com.quickticket.quickticket.domain.user.domain.User;
import com.quickticket.quickticket.shared.annotations.Default;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

/// Credit 원장에서 잔고 변동 각각을 나타내는 데이터
@Getter
@AllArgsConstructor(onConstructor_ = {@Default})
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

    @Builder(builderMethodName = "create")
    public CreditTransaction(
        TransactionType transactionType,
        Long changeAmount,
        Long balanceAfter
    ) {
        validateBalanceAfter(balanceAfter);

        this.type = transactionType;
        this.changeAmount = changeAmount;
        this.balanceAfter = balanceAfter;
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