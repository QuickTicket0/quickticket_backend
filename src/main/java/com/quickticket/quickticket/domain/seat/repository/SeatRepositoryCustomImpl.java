package com.quickticket.quickticket.domain.seat.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.quickticket.quickticket.domain.seat.domain.Seat;
import com.quickticket.quickticket.domain.seat.dto.SeatClassCache;
import com.quickticket.quickticket.domain.seat.entity.QSeatClassEntity;
import com.quickticket.quickticket.domain.seat.mapper.SeatMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SeatRepositoryCustomImpl implements SeatRepositoryCustom {
    private final SeatMapper seatMapper;
    private final JPAQueryFactory queryFactory;

    @PersistenceContext
    private final EntityManager em;

    /**
     * seat ID로 조회하여 SeatClassCache 변환하여 반환
     * @param seatCacheCacheId 조회할 seat의 PK
     * @return 조회된 seat 정보를 담은 SeatClassCache (없을 경우엔 null로 반환)
     */
    public SeatClassCache getSeatClassCacheId(Long seatClassCacheId) {
        var seatClass = QSeatClassEntity.seatClassEntity;

        var query =  Optional.ofNullable(queryFactory
                    .select(seatClass)
                    .from(seatClass)
                    .where(seatClass.id.seatClassId.eq(seatClassCacheId))
                    .fetchOne()
            )
            .map(e -> new SeatClassCache(
                    e.getId().getSeatClassId(),
                    e.getEvent().getEventId(),
                    e.getName(),
                    e.getPrice()
            ))
            .orElse(null);
        return query;
    }

    @Override
    public Seat saveDomain(Seat domain) {
        var entity = seatMapper.toEntity(domain);

        if (entity.getId().getSeatId() == null) {
            em.persist(entity);
        } else {
            em.merge(entity);
        }

        return seatMapper.toDomain(entity);
    }
}
