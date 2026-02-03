package com.quickticket.quickticket.domain.seat.entity;

import com.quickticket.quickticket.domain.seat.dto.SeatClassCache;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SeatClassCacheListener {
    private final CacheManager cacheManager;

    @PostUpdate
    public void updateCache(SeatClassEntity entity) {
        var cache = cacheManager.getCache("seatClass_cacheDto");
        if (cache == null) return;

        cache.put(
            entity.getId().getSeatClassId() + ":" + entity.getId().getEventId(),
            SeatClassCache.builder()
                .id(entity.getId().getSeatClassId())
                .eventId(entity.getId().getEventId())
                .price(entity.getPrice())
                .name(entity.getName())
                .build()
        );
    }

    @PostRemove
    public void clearCache(SeatClassEntity entity) {
        var cache = cacheManager.getCache("seatClass_cacheDto");
        if (cache == null) return;

        cache.evict(entity.getId().getSeatClassId() + ":" + entity.getId().getEventId());
    }
}
