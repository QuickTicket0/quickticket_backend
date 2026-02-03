package com.quickticket.quickticket.domain.seat.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.quickticket.quickticket.domain.seat.domain.Seat;
import com.quickticket.quickticket.domain.seat.dto.SeatCache;
import com.quickticket.quickticket.domain.seat.dto.SeatClassCache;
import com.quickticket.quickticket.domain.seat.entity.QSeatClassEntity;
import com.quickticket.quickticket.domain.seat.entity.QSeatEntity;
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
     * @param seatId 조회할 seat의 PK
     * @return 조회된 seat 정보를 담은 SeatClassCache (없을 경우엔 null로 반환)
     */
    public SeatCache getCacheById(Long seatId, Long performanceId) {
        var seat = QSeatEntity.seatEntity;

        var query = Optional.ofNullable(queryFactory
                .selectFrom(seat)
                .where(
                        seat.id.seatId.eq(seatId),
                        seat.id.performanceId.eq(performanceId)
                )
                .fetchOne()).orElseThrow();

        return SeatCache.builder()
                .id(seatId)
                .performanceId(performanceId)
                .currentWaitingNumber(query.getCurrentWaitingNumber())
                .name(query.getName())
                .seatAreaId(query.getArea().getId().getSeatAreaId())
                .seatClassId(query.getSeatClass().getId().getSeatClassId())
                .status(query.getStatus())
                .build();
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
