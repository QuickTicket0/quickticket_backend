package com.quickticket.quickticket.domain.event.repository;

import com.quickticket.quickticket.domain.event.dto.EventCache;

public interface EventRepositoryCustom {
    EventCache getEventCacheId(Long eventCacheId);
}