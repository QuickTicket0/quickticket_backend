package com.quickticket.quickticket.domain.ticket.domain;

import com.quickticket.quickticket.domain.payment.method.domain.PaymentMethod;
import com.quickticket.quickticket.domain.performance.domain.Performance;
import com.quickticket.quickticket.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Ticket {
    private Long id;
    private Performance performance;
    private User user;
    private PaymentMethod paymentMethod;
    private TicketStatus status;
    private Long waitingNumber;
    private Integer personNumber;

    /// 반드시 create로 생성된 객체가 DB에 할당되었을 상황에만 호출하세요
    public void assignId(Long id) {
        if (this.id != null) throw new IllegalStateException();

        this.id = id;
    }
}