package com.quickticket.quickticket.domain.seat.repository;

import com.quickticket.quickticket.domain.seat.domain.Seat;

public interface SeatRepositoryCustom {
    Seat saveDomain(Seat domain);
}
