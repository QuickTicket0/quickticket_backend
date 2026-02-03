package com.quickticket.quickticket.domain.category.entity;

import com.quickticket.quickticket.domain.category.dto.CategoryCommonDto;
import com.quickticket.quickticket.domain.event.entity.EventEntity;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryCacheListener {
    private final CacheManager cacheManager;

    @PostUpdate
    public void updateCache(CategoryEntity entity) {
        var cache = cacheManager.getCache("category_commonDto");
        if (cache == null) return;

        cache.put(entity.getCategoryId(), CategoryCommonDto.from(entity));
    }

    @PostRemove
    public void clearCache(CategoryEntity entity) {
        var cache = cacheManager.getCache("category_commonDto");
        if (cache == null) return;

        cache.evict(entity.getCategoryId());
    }
}
