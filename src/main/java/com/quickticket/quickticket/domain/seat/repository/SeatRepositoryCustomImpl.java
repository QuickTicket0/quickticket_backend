package com.quickticket.quickticket.domain.seat.repository;

import com.quickticket.quickticket.domain.seat.domain.Seat;
import com.quickticket.quickticket.domain.seat.mapper.SeatMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SeatRepositoryCustomImpl implements SeatRepositoryCustom {
    private final SeatMapper seatMapper;

    @PersistenceContext
    private final EntityManager em;

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
