package com.quickticket.quickticket.domain.event.repository;

import com.quickticket.quickticket.domain.event.dto.EventCache;
import org.springframework.cache.annotation.Cacheable;

public interface EventRepositoryCustom {
    @Cacheable(value = "cache:event-cache", key = "#eventId")
    EventCache getCacheById(Long eventId);
}
