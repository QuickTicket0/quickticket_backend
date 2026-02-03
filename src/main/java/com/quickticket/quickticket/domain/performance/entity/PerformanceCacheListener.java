package com.quickticket.quickticket.domain.performance.entity;

import com.quickticket.quickticket.domain.location.entity.LocationEntity;
import com.quickticket.quickticket.domain.performance.dto.PerformanceCache;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PerformanceCacheListener {
    private final CacheManager cacheManager;

    @PostUpdate
    public void updateCache(PerformanceEntity entity) {
        var performanceCache = cacheManager.getCache("performance_cacheDto");
        if (performanceCache == null) return;

        performanceCache.put(
            entity.getPerformanceId(),
            PerformanceCache.builder()
                .id(entity.getPerformanceId())
                .nth(entity.getPerformanceNth())
                .performanceStartsAt(entity.getPerformanceStartsAt().toString())
                .ticketingStartsAt(entity.getTicketingStartsAt().toString())
                .ticketingEndsAt(entity.getTicketingEndsAt().toString())
                .performersName(entity.getPerformersName())
                .runningTime(entity.getRunningTime().toString())
                .build()
        );
    }

    @PostRemove
    public void clearCache(PerformanceEntity entity) {
        var performanceCache = cacheManager.getCache("performance_cacheDto");
        if (performanceCache == null) return;

        performanceCache.evict(entity.getPerformanceId());
    }
}
