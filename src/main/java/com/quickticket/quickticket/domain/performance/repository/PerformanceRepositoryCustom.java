package com.quickticket.quickticket.domain.performance.repository;

import com.quickticket.quickticket.domain.performance.domain.Performance;
import com.quickticket.quickticket.domain.performance.dto.PerformanceCache;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

public interface PerformanceRepositoryCustom {
    @Cacheable(value = "cache:performance-cache", key = "#performanceId")
    PerformanceCache getCacheById(Long performanceId);

    @Cacheable(value = "stat:performance-ticket-waiting-length", key = "#performanceId")
    Long getWaitingLengthOfPerformance(Long performanceId);

    Performance saveDomain(Performance domain);
}
