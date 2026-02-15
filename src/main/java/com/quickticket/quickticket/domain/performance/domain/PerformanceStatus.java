package com.quickticket.quickticket.domain.performance.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PerformanceStatus {
    PENDING(0), TICKETING(1), TICKETING_ENDED(2), TICKETING_ENDED_EARLY(3);

    private final int code;
}