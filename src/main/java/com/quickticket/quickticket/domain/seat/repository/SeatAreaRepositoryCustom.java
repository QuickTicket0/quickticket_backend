package com.quickticket.quickticket.domain.seat.repository;

import com.quickticket.quickticket.domain.seat.dto.SeatAreaCache;
import org.springframework.cache.annotation.Cacheable;

public interface SeatAreaRepositoryCustom {
    @Cacheable(value = "cache:seat-area-cache", key = "#seatAreaId:#eventId")
    SeatAreaCache getCacheById(Long seatAreaId, Long eventId);
}
