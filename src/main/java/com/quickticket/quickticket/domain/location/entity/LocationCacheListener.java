package com.quickticket.quickticket.domain.location.entity;

import com.quickticket.quickticket.domain.event.entity.EventEntity;
import com.quickticket.quickticket.domain.location.dto.LocationCommonDto;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LocationCacheListener {
    @Lazy
    private final CacheManager cacheManager;

    @PostUpdate
    public void updateCache(LocationEntity entity) {
        var cache = cacheManager.getCache("Location_commonDto");
        if (cache == null) return;

        cache.put(entity.getLocationId(), LocationCommonDto.from(entity));
    }

    @PostRemove
    public void clearCache(LocationEntity entity) {
        var cache = cacheManager.getCache("Location_commonDto");
        if (cache == null) return;

        cache.evict(entity.getLocationId());
    }
}
