package com.quickticket.quickticket.domain.seat.repository;

import com.quickticket.quickticket.domain.seat.domain.Seat;
import com.quickticket.quickticket.domain.seat.mapper.SeatMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public abstract class SeatRepositoryImpl implements SeatRepository {
    private final SeatMapper seatMapper;

    public Seat saveDomain(Seat domain) {
        var entity = seatMapper.toEntity(domain);
        this.save(entity);

        return seatMapper.toDomain(entity);
    }
}
