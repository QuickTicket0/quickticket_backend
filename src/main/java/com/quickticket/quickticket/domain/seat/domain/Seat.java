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
    private String name;
    private Performance performance;
    private SeatClass seatClass;
    private SeatArea seatArea;
    private SeatStatus status;
    /// 한 공연 회차의 좌석에 대해, 지금 선착순 몇빈째가 배정되었는지의 번호.
    ///
    /// waitingNumber는 곧 Ticket이 Performance에서 몇빈째 선착순으로 들어왔는지 나타냅니다.
    /// 각 Ticket은 저마다 다른 좌석을 선택했기 때문에, 한 좌석에서 배정된 waitingNumber는 순서대로 좌석 정보를 파악하면서,
    /// 이 좌석을 선택한 Ticket이 나올때 비로소 그 waitingNumber로 설정됩니다.
    /// 즉 currentWaitingNumber는 1 다음에 2라는 보장이 없으며, 3이나 4로 될 수도 있습니다.
    /// 그건 그 대기순번의 Ticket이 이 좌석을 선택했느냐에 달렸습니다.
    private Long currentWaitingNumber;

    /// 반드시 create로 생성된 객체가 DB에 할당되었을 상황에만 호출하세요
    public void assignId(Long id) {
        if (this.id != null) throw new IllegalStateException();

        this.id = id;
    }

    public static Seat create(
        Performance performance,
        String name,
        SeatClass seatClass,
        SeatArea seatArea
    ) {
        return Seat.builder()
                .performance(performance)
                .name(name)
                .seatClass(seatClass)
                .seatArea(seatArea)
                .build();
    }

    public static Seat recreate(
            Long id,
            String name,
            Performance performance,
            SeatClass seatClass,
            SeatArea seatArea,
            Long currentWaitingNumber
    ) {
        return Seat.builder()
                .id(id)
                .name(name)
                .performance(performance)
                .seatClass(seatClass)
                .seatArea(seatArea)
                .currentWaitingNumber(currentWaitingNumber)
                .build();
    }
}
