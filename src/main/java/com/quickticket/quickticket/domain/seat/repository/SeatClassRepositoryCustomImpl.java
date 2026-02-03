package com.quickticket.quickticket.domain.seat.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.quickticket.quickticket.domain.seat.dto.SeatClassCache;
import com.quickticket.quickticket.domain.seat.entity.QSeatClassEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SeatClassRepositoryCustomImpl implements SeatClassRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public SeatClassCache getCacheById(Long seatClassId, Long eventId) {
        var seatClass = QSeatClassEntity.seatClassEntity;

        var query = Optional.ofNullable(queryFactory
                .selectFrom(seatClass)
                .where(
                        seatClass.id.seatClassId.eq(seatClassId),
                        seatClass.id.eventId.eq(eventId)
                )
                .fetchOne()).orElseThrow();

        return SeatClassCache.builder()
                .id(seatClassId)
                .eventId(eventId)
                .name(query.getName())
                .price(query.getPrice())
                .build();
    }
}
