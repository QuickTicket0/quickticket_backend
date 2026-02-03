package com.quickticket.quickticket.domain.seat.entity;

import com.quickticket.quickticket.domain.location.entity.LocationEntity;
import com.quickticket.quickticket.domain.seat.dto.SeatCache;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SeatCacheListener {
    private final CacheManager cacheManager;

    @PostUpdate
    public void updateCache(SeatEntity entity) {
        var cache = cacheManager.getCache("seat_cacheDto");
        if (cache == null) return;

        cache.put(
            entity.getId().getSeatId() + ":" + entity.getId().getPerformanceId(),
            SeatCache.builder()
                .id(entity.getId().getSeatId())
                .performanceId(entity.getId().getPerformanceId())
                .status(entity.getStatus())
                .seatClassId(entity.getSeatClass().getId().getSeatClassId())
                .seatAreaId(entity.getArea().getId().getSeatAreaId())
                .name(entity.getName())
                .currentWaitingNumber(entity.getCurrentWaitingNumber())
                .build()
        );
    }

    @PostRemove
    public void clearCache(SeatEntity entity) {
        var cache = cacheManager.getCache("seat_cacheDto");
        if (cache == null) return;

        cache.evict(entity.getId().getSeatId() + ":" + entity.getId().getPerformanceId());
    }
}
