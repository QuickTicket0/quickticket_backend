package com.quickticket.quickticket.domain.seat.repository;

import com.quickticket.quickticket.domain.seat.dto.SeatAreaCache;
import com.quickticket.quickticket.domain.seat.entity.*;
import com.quickticket.quickticket.shared.utils.BaseCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SeatAreaRepositoryCustomImpl
        extends BaseCustomRepository<SeatAreaEntity, SeatAreaId>
        implements SeatAreaRepositoryCustom {

    public SeatAreaCache getCacheById(Long seatAreaId, Long eventId) {
        var query = getEntityById(new SeatAreaId(seatAreaId, eventId)).orElseThrow();

        return SeatAreaCache.builder()
                .id(query.getId().getSeatAreaId())
                .eventId(query.getId().getEventId())
                .name(query.getName())
                .build();
    }
}
