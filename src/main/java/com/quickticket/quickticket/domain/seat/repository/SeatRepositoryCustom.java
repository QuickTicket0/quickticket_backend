package com.quickticket.quickticket.domain.seat.repository;

import com.quickticket.quickticket.domain.seat.domain.Seat;
import com.quickticket.quickticket.domain.seat.dto.SeatCache;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

public interface SeatRepositoryCustom {
    @Cacheable(value = "seat_cacheDto", key = "#seatId #performanceId")
    SeatCache getCacheById(Long seatId, Long performanceId);

    Seat saveDomain(Seat domain);
}
