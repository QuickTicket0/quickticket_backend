package com.quickticket.quickticket.domain.seat.repository;

import com.quickticket.quickticket.domain.seat.domain.Seat;
import com.quickticket.quickticket.domain.seat.dto.SeatClassCache;

public interface SeatRepositoryCustom {
    SeatClassCache getSeatClassCacheId(Long seatCacheCacheId);

    Seat saveDomain(Seat domain);
}
