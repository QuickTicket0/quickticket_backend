package com.quickticket.quickticket.domain.ticket.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.quickticket.quickticket.domain.seat.entity.QSeatEntity;
import com.quickticket.quickticket.domain.ticket.entity.QWantingSeatsEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class WantingSeatsRepositoryImpl implements WantingSeatsRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Long> getSeatIdsByTicketIssueId(Long ticketId) {
        var wantingSeat = QWantingSeatsEntity.wantingSeatsEntity;

        return Optional.ofNullable(queryFactory
                .select(wantingSeat.seat.id.seatId)
                .from(wantingSeat)
                .where(wantingSeat.ticketIssue.ticketIssueId.eq(ticketId))
                .fetch()).orElseThrow();
    }
}
