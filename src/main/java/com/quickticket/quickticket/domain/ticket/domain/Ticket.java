package com.quickticket.quickticket.domain.ticket.domain;

import com.quickticket.quickticket.domain.payment.method.domain.PaymentMethod;
import com.quickticket.quickticket.domain.performance.domain.Performance;
import com.quickticket.quickticket.domain.seat.domain.Seat;
import com.quickticket.quickticket.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.List;

/// 한 Performance에 대해 사용자가 여러개의 좌석을 선택하고 선착순을 기다리는 예매 정보
///
/// Ticket은 아직 예매가 시작되지 않은 상태에서의 사전 설정 정보도 저장합니다.
/// 따라서 Ticket이란 객체는 반드시 예매중인 Performance의 선착순으로 들어가 있다는 보장이 없습니다.
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
    private LocalDateTime createdAt;
    private LocalDateTime canceledAt;
    private List<Seat> wantingSeats;

    /// 반드시 create로 생성된 객체가 DB에 할당되었을 상황에만 호출하세요
    public void assignId(Long id) {
        if (this.id != null) throw new IllegalStateException();

        this.id = id;
    }
}