package com.quickticket.quickticket.domain.ticket.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

public class TicketRequest {
    /// 사용자가 예매가 시작되기 전 예매 정보들을 사전 설정할때
    @Builder
    public record Preset(
        @NotNull
        @Min(0)
        Long performanceId,

        @Min(0)
        Long paymentMethodId,

        @Min(1)
        Integer personNumber
    ) {}

    /// 사용자가 예매가 열린 상황에서 실제 예매를 할때
    @Builder
    public record Ticket(
        @NotNull
        @Min(0)
        Long performanceId,

        @NotNull
        @Min(0)
        Long paymentMethodId,

        @NotNull
        @Min(1)
        Integer personNumber
    ) {}

    @Builder
    public record Cancel(
        @NotNull
        @Min(0)
        Long id
    ) {}
}