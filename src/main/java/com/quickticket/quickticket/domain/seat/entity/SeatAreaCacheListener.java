package com.quickticket.quickticket.domain.seat.entity;

import com.quickticket.quickticket.domain.seat.dto.SeatAreaCache;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SeatAreaCacheListener {
    private final CacheManager cacheManager;

    @PostUpdate
    public void updateCache(SeatAreaEntity entity) {
        var cache = cacheManager.getCache("seatArea_cacheDto");
        if (cache == null) return;

        cache.put(
            entity.getId().getSeatAreaId() + ":" + entity.getId().getEventId(),
            SeatAreaCache.builder()
                .id(entity.getId().getSeatAreaId())
                .eventId(entity.getId().getEventId())
                .name(entity.getName())
                .build()
        );
    }

    @PostRemove
    public void clearCache(SeatAreaEntity entity) {
        var cache = cacheManager.getCache("seatArea_cacheDto");
        if (cache == null) return;

        cache.evict(entity.getId().getSeatAreaId() + ":" + entity.getId().getEventId());
    }
}
