package com.quickticket.quickticket.domain.performance.domain;

import com.quickticket.quickticket.domain.event.domain.Event;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

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