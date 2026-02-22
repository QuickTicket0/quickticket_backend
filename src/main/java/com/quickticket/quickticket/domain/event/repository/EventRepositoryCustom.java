package com.quickticket.quickticket.domain.event.repository;

import com.quickticket.quickticket.domain.event.dto.EventCache;
import com.quickticket.quickticket.domain.event.entity.EventEntity;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface EventRepositoryCustom {
    @Cacheable(value = "cache:event-cache", key = "#eventId")
    EventCache getCacheById(Long eventId);

    // 마감 임박 공연 상위 N개를 가져오는 메서드
    List<EventEntity> findClosingSoonEvents(int limit);
}
