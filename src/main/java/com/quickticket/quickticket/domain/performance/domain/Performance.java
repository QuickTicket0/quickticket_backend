package com.quickticket.quickticket.domain.performance.domain;

import com.quickticket.quickticket.domain.event.domain.Event;
import com.quickticket.quickticket.shared.annotations.Default;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor(onConstructor_ = {@Default})
public class Performance {
    private Long id;
    private Event event;
    /// 공연의 회차 번째. 회차 번째는 하루 단위로 정해집니다. (1월 10일 1회차, 1월 10일 2회차, 1월 11일 1회차)
    private Integer nth;
    /// 공연 회차에 수용하고자 하는 인원 수.
    private Integer targetSeatNumber;
    /// 공연자 이름 목록
    private List<String> performersName;
    private LocalDateTime ticketingStartsAt;
    private LocalDateTime ticketingEndsAt;
    /// 공연 시작 시간
    private LocalDateTime performanceStartsAt;
    /// 공연 진행 시간
    private LocalTime runningTime;
    /// 현재 이 공연 회차에 예매한 사람 수.
    /// 대기 순번이 올라가도 웨이팅 길이를 바꾸지 않습니다. 취소표가 나든 배정받든 전체 예매의 수만 나타냅니다.
    private Long ticketWaitingLength;

    public PerformanceStatus getStatus() {
        if (LocalDateTime.now().isBefore(this.ticketingStartsAt)) {
            return PerformanceStatus.PENDING;
        }
        if (LocalDateTime.now().isAfter(this.ticketingEndsAt)) {
            return PerformanceStatus.TICKETING_ENDED;
        }

        // 현재 예매중인 상태

        if (this.ticketWaitingLength >= this.targetSeatNumber) {
            return PerformanceStatus.TICKETING_ENDED_EARLY;
        } else {
            return PerformanceStatus.TICKETING;
        }
    }

    public void setTicketWaitingLength(Long waitingLength) {
        this.ticketWaitingLength = waitingLength;
    }
}