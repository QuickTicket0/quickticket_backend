package com.quickticket.quickticket.domain.seat.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.quickticket.quickticket.domain.seat.dto.SeatClassCache;
import com.quickticket.quickticket.domain.seat.entity.*;
import com.quickticket.quickticket.shared.utils.BaseCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SeatClassRepositoryCustomImpl
        extends BaseCustomRepository<SeatClassEntity, SeatClassId>
        implements SeatClassRepositoryCustom {

    public SeatClassCache getCacheById(Long seatClassId, Long eventId) {
        var query = getEntityById(new SeatClassId(seatClassId, eventId)).orElseThrow();

        return SeatClassCache.builder()
                .id(seatClassId)
                .eventId(eventId)
                .name(query.getName())
                .price(query.getPrice())
                .build();
    }
}
