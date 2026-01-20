package com.quickticket.quickticket.domain.performance.domain;

import com.quickticket.quickticket.domain.event.domain.Event;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/// Event에서 파생되는 각 일자별 공연 회차의 단위.
/// 회차에 따라서 달라질 수 있는 정보들, 가령 연기자는 특정 요일에만 나올수도 있기 때문에 그런건 Performance에 저장합니다
/// 또 예매에 대한 것들, 가령 예매 순번같은건 전적으로 회차에 대해 이루어지고 순서가 매겨집니다.
@Builder
@Getter
public class Performance {
    private Long id;
    private Event event;
    private Integer nth;
    private Integer targetSeatNumber;
    private List<String> performersName;
    private LocalDateTime ticketingStartsAt;
    private LocalDateTime ticketingEndsAt;
    private LocalDateTime performanceStartsAt;
    private LocalTime runningTime;

    /// 반드시 create로 생성된 객체가 DB에 할당되었을 상황에만 호출하세요
    public void assignId(Long id) {
        if (this.id != null) throw new IllegalStateException();

        this.id = id;
    }
}