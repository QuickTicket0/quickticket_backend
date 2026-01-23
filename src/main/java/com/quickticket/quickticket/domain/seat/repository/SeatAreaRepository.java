package com.quickticket.quickticket.domain.seat.repository;

import com.quickticket.quickticket.domain.seat.entity.SeatAreaEntity;
import com.quickticket.quickticket.domain.seat.entity.SeatAreaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatAreaRepository extends JpaRepository<SeatAreaEntity, SeatAreaId> {
}
