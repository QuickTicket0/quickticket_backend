package com.quickticket.quickticket.domain.seat.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.quickticket.quickticket.domain.seat.dto.SeatAreaCache;
import com.quickticket.quickticket.domain.seat.entity.QSeatAreaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SeatAreaRepositoryCustomImpl implements SeatAreaRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public SeatAreaCache getCacheById(Long seatAreaId, Long eventId) {
        var seatArea = QSeatAreaEntity.seatAreaEntity;

        var query = Optional.ofNullable(queryFactory
                .selectFrom(seatArea)
                .where(
                        seatArea.id.seatAreaId.eq(seatAreaId),
                        seatArea.id.eventId.eq(eventId)
                )
                .fetchOne()).orElseThrow();

        return SeatAreaCache.builder()
                .id(query.getId().getSeatAreaId())
                .eventId(query.getId().getEventId())
                .name(query.getName())
                .build();
    }
}
