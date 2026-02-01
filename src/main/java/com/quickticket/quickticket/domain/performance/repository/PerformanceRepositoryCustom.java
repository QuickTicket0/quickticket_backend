package com.quickticket.quickticket.domain.performance.repository;

import com.quickticket.quickticket.domain.performance.domain.Performance;

public interface PerformanceRepositoryCustom {
    PerformanceCache getPerformanceCacheId(Long performanceCacheId);

    Long getWaitingLengthOfPerformance(Long performanceId);

    Performance saveDomain(Performance domain);
}
