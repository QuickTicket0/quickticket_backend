package com.quickticket.quickticket.domain.payment.seatPayment.domain;

import com.quickticket.quickticket.domain.performance.domain.Performance;
import com.quickticket.quickticket.domain.seat.domain.Seat;
import com.quickticket.quickticket.domain.ticket.domain.Ticket;
import com.quickticket.quickticket.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;

/// Ticket으로 예매를 한 좌석중에서 실제로 좌석이 비어 결제에 이르렀을때,
/// 그 결제에 관한 정보를 다루기 위한 도메인 객체
///
/// SeatPaymentIssue는 Ticket에서 원하는 자리에 선착순이 올때 비로소 생성됩니다.
@Builder
@Getter
public class SeatPaymentIssue {
    private Long id;
    private Ticket ticketIssue;
    private User user;
    private Seat seat;
    private SeatPaymentIssueStatus status;

    /// 반드시 create로 생성된 객체가 DB에 할당되었을 상황에만 호출하세요
    public void assignId(Long id) {
        if (this.id != null) throw new IllegalStateException();

        this.id = id;
    }
}