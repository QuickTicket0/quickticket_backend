package com.quickticket.quickticket.domain.seat.repository;

import com.quickticket.quickticket.domain.seat.dto.SeatClassCache;
import org.springframework.cache.annotation.Cacheable;

public interface SeatClassRepositoryCustom {
    @Cacheable(value = "cache:seat-class-cache", key = "#seatClassId:#eventId")
    SeatClassCache getCacheById(Long seatClassId, Long eventId);
}
