package com.quickticket.quickticket.domain.payment.seatPayment.domain;

import com.quickticket.quickticket.domain.performance.domain.Performance;
import com.quickticket.quickticket.domain.seat.domain.Seat;
import com.quickticket.quickticket.domain.ticket.domain.Ticket;
import com.quickticket.quickticket.domain.user.domain.User;
import com.quickticket.quickticket.shared.annotations.Default;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/// Ticket으로 예매를 한 좌석중에서 실제로 좌석이 비어 결제에 이르렀을때,
/// 그 결제에 관한 정보를 다루기 위한 도메인 객체
///
/// SeatPaymentIssue는 Ticket에서 원하는 자리에 선착순이 올때 비로소 생성됩니다.
@Getter
@AllArgsConstructor(onConstructor_ = {@Default})
public class SeatPaymentIssue {
    private Long id;
    private Ticket ticketIssue;
    private User user;
    /// 결제하려는 좌석
    private Seat seat;
    /// 결제 상태
    private SeatPaymentIssueStatus status;
    private Long amount;
    private LocalDateTime createdAt;

    @Builder(builderMethodName = "createAndPay")
    public SeatPaymentIssue(
            Ticket ticketIssue,
            User user,
            Seat seat,
            Long amount
    ) {
        this.ticketIssue = ticketIssue;
        this.user = user;
        this.seat = seat;
        this.amount = amount;

        this.pay();
    }

    private void pay() {
        var paymentMethod = this.ticketIssue.getPaymentMethod();

        // TODO 각 결제수단별 지불 로직 구현하기

        switch (paymentMethod.getType()) {
            case CREDIT -> {

            }
            case CARD -> {

            }
            default -> {}
        }
    }
}