package com.quickticket.quickticket.domain.seat.domain;

import com.quickticket.quickticket.domain.payment.credit.domain.CreditTransaction;
import com.quickticket.quickticket.domain.payment.credit.domain.TransactionType;
import com.quickticket.quickticket.domain.performance.domain.Performance;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

/// Performance를 기준으로 파생되는 한 좌석의 정보
///
/// Seat는 기본적으로 회차에서 발생한 예매들에서 몇번째 순번을 이 좌석에 지정할건지의 정보도 갖습니다.
@Builder(access = PRIVATE)
@Getter
public class Seat {
    private Long id;
    private Performance performance;
    private SeatClass seatClass;
    private SeatArea seatArea;
    private Long currentWaitingNumber;

    /// 반드시 create로 생성된 객체가 DB에 할당되었을 상황에만 호출하세요
    public void assignId(Long id) {
        if (this.id != null) throw new IllegalStateException();

        this.id = id;
    }

    public static Seat create(
        Performance performance,
        SeatClass seatClass,
        SeatArea seatArea
    ) {
        return Seat.builder()
                .performance(performance)
                .seatClass(seatClass)
                .seatArea(seatArea)
                .build();
    }

    public static Seat recreate(
            Long id,
            Performance performance,
            SeatClass seatClass,
            SeatArea seatArea,
            Long currentWaitingNumber
    ) {
        return Seat.builder()
                .id(id)
                .performance(performance)
                .seatClass(seatClass)
                .seatArea(seatArea)
                .currentWaitingNumber(currentWaitingNumber)
                .build();
    }
}
