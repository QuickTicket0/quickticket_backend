package com.quickticket.quickticket.domain.performance.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class PerformanceRequest {
    /// 어드민이 Event 생성시 이의 각 회차를 생성할때
    @Builder
    public record Create(
        @NotNull
        @Min(0)
        Long eventId,

        @NotNull
        @Min(1)
        Integer nth,

        @Min(1)
        Integer targetSeatNumber,

        List<String> performersName,

        @NotNull
        @FutureOrPresent
        LocalDateTime ticketingStartsAt,

        @NotNull
        @FutureOrPresent
        LocalDateTime ticketingEndsAt,

        @NotNull
        @FutureOrPresent
        LocalDateTime performanceStartsAt,

        @NotNull
        LocalTime runningTime
    ) {}

    @Builder
    public record Edit(
        @NotNull
        @Min(0)
        Long id,

        @Min(1)
        Integer nth,

        @Min(1)
        Integer targetSeatNumber,

        List<String> performersName,
        
        LocalDateTime ticketingStartsAt,
        
        LocalDateTime ticketingEndsAt,
        
        LocalDateTime performanceStartsAt,
        
        LocalTime runningTime
    ) {}

    @Builder
    public record Delete(
        @NotNull
        @Min(0)
        Long id
    ) {}
}