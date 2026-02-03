package com.quickticket.quickticket.domain.event.entity;

import com.quickticket.quickticket.domain.event.dto.EventCache;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RedissonClient;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventCacheListener {
    private final CacheManager cacheManager;

    @PostUpdate
    public void updateCache(EventEntity entity) {
        var cache = cacheManager.getCache("event_cacheDto");
        if (cache == null) return;

        cache.put(
            entity.getEventId(),
            EventCache.builder()
                .id(entity.getEventId())
                .name(entity.getName())
                .ageRating(entity.getAgeRating())
                .locationId(entity.getLocation().getLocationId())
                .build()
        );
    }

    @PostRemove
    public void clearCache(EventEntity entity) {
        var cache = cacheManager.getCache("event_cacheDto");
        if (cache == null) return;

        cache.evict(entity.getEventId());
    }
}
