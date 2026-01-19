package com.quickticket.quickticket.domain.seatPaymentIssue.domain;

import com.quickticket.quickticket.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SeatPaymentIssue {
    private Long id;
    private Perormance performance;
    private User user;
    private Seat seat;
    private SeatPaymentIssueStatus status;

    /// 반드시 create로 생성된 객체가 DB에 할당되었을 상황에만 호출하세요
    public void assignId(Long id) {
        if (this.id != null) throw new IllegalStateException();

        this.id = id;
    }
}