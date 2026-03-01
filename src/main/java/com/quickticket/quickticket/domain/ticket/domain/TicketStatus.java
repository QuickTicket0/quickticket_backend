package com.quickticket.quickticket.domain.ticket.domain;

import com.quickticket.quickticket.shared.converters.OrdinalEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TicketStatus implements OrdinalEnum {
    WAITING(0, "예매대기"),
    PRESET(1, "사전 설정"),
    CANCELED(2, "예매 취소"),
    SEAT_ALLOCATED_ALL(3, "좌석 배정 완료"),
    SEAT_ALLOCATED_PARTIAL(4, "좌석 부분 배정");

    public final int code;
    private final String description;
}
