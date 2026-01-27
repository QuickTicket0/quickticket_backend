package com.quickticket.quickticket.domain.ticket.domain;

import com.quickticket.quickticket.domain.payment.method.domain.PaymentMethod;
import com.quickticket.quickticket.domain.performance.domain.Performance;
import com.quickticket.quickticket.domain.seat.domain.Seat;
import com.quickticket.quickticket.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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
    /// 예매 대기순번에서 이 예매가 선착순 몇번째인지의 번호.
    /// 한 Performance 안에서는 각 대기순번마다 고유한 Ticket이 있습니다.
    /// waitingNumber는 한번 배정받으면 변경되지 않습니다. Seat에서 배정되는 숫자가 올라갈 뿐이지 모든 Ticket들의
    /// 대기순번이 내려오지는 않습니다.
    private Long waitingNumber;
    private Integer personNumber;
    /// 예매가 이뤄졌을시 null이 아님
    private LocalDateTime createdAt;
    /// 예매 취소했을시 null이 아님
    private LocalDateTime canceledAt;
    /// 배정받길 원하는 좌석.
    /// 원하는 좌석 내에 모든 좌석들이 배정받는 것은 아닙니다.
    /// 인원이 2명이라면 그 범위 내에서 가장 빨리 선착순이 난 좌석 2개를 차례대로 배정받습니다.
    /// 키: Seat의 id
    private Map<Long, Seat> wantingSeats;

    public boolean isCanceled() {
        return this.status == TicketStatus.CANCELED
                || this.canceledAt != null;
    }

    public void cancel() {
        if (isCanceled()) throw new IllegalStateException("이미 취소된 티켓입니다.");

        this.status = TicketStatus.CANCELED;
        this.canceledAt = LocalDateTime.now();
    }

    public void ticketToPerformance(Performance performance) {
        this.performance = performance;
        this.status = TicketStatus.WAITING;
        this.waitingNumber = performance.getTicketWaitingLength() + 1;
        this.createdAt = LocalDateTime.now();

        performance.addOneToWaitingLength();
    }

    public void allocateSeat(Seat seat) {
        seat.setWaitingNumberTo(this.waitingNumber);

        this.updateAllocationStatus();
    }

    private void updateAllocationStatus() {
        var seatAllocatedToThisCount = 0;

        for (var seat: this.wantingSeats.values()) {
            if (seat.getCurrentWaitingNumber() == this.waitingNumber) {
                seatAllocatedToThisCount++;
            }
        }

        if (seatAllocatedToThisCount == this.wantingSeats.size()) {
            this.status = TicketStatus.SEAT_ALLOCATED_ALL;
        } else {
            this.status = TicketStatus.SEAT_ALLOCATED_PARTIAL;
        }
    }
}
