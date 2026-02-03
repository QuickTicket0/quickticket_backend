package com.quickticket.quickticket.domain.performance.dto;

import java.util.List;

public record PerformanceCache(
    Integer nth,
    String ticketingStartsAt,
    String ticketingEndsAt,
    String performanceStartsAt,
    String runningTime,
    List<String> performersName
) {}
