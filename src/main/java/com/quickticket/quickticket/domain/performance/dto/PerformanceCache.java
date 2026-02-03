package com.quickticket.quickticket.domain.performance.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record PerformanceCache(
    Integer nth,
    String ticketingStartsAt,
    String ticketingEndsAt,
    String performanceStartsAt,
    String runningTime,
    List<String> performersName
) {}
