package com.quickticket.quickticket.domain.ticket.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.quickticket.quickticket.domain.seat.entity.QSeatEntity;
import com.quickticket.quickticket.domain.seat.entity.SeatEntity;
import com.quickticket.quickticket.domain.ticket.domain.Ticket;
import com.quickticket.quickticket.domain.ticket.domain.TicketStatus;
import com.quickticket.quickticket.domain.ticket.entity.QTicketIssueEntity;
import com.quickticket.quickticket.domain.ticket.entity.QWantingSeatsEntity;
import com.quickticket.quickticket.domain.ticket.mapper.TicketIssueMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class WantingSeatsRepositoryCustomImpl implements WantingSeatsRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private final TicketIssueMapper ticketIssueMapper;

    private List<SeatEntity> getSeatEntitiesByTicketIssueId(Long ticketId) {
        var wantingSeat = QWantingSeatsEntity.wantingSeatsEntity;
        var seat = QSeatEntity.seatEntity;

        return queryFactory
                .select(seat)
                .from(wantingSeat)
                .where(
                        wantingSeat.ticketIssue.ticketIssueId.eq(ticketId),
                        wantingSeat.seat.id.seatId.eq(seat.id.seatId)
                )
                .fetch();
    }

    @Override
    public List<Long> getSeatIdsByTicketIssueId(Long ticketId) {
        var wantingSeat = QWantingSeatsEntity.wantingSeatsEntity;

        return Optional.ofNullable(queryFactory
                .select(wantingSeat.seat.id.seatId)
                .from(wantingSeat)
                .where(wantingSeat.ticketIssue.ticketIssueId.eq(ticketId))
                .fetch()).orElseThrow();
    }

    @Override
    public Ticket getNextTicketWantingTheSeatOrNull(Long fromNth, Long performanceId, Long seatId) {
        var ticket = QTicketIssueEntity.ticketIssueEntity;
        var wantingSeat = QWantingSeatsEntity.wantingSeatsEntity;

        var fetchOne = queryFactory
                .selectFrom(ticket)
                .innerJoin(wantingSeat).on(ticket.ticketIssueId.eq(wantingSeat.ticketIssue.ticketIssueId))
                .where(
                        ticket.performance.performanceId.eq(performanceId),
                        wantingSeat.seat.id.seatId.eq(seatId),
                        ticket.waitingNumber.gt(fromNth),
                        ticket.status.ne(TicketStatus.CANCELED)
                )
                .orderBy(ticket.waitingNumber.asc())
                .fetchFirst();

        if (fetchOne == null) return null;

        var wantingSeats = this.getSeatEntitiesByTicketIssueId(fetchOne.getTicketIssueId());

        return ticketIssueMapper.toDomain(fetchOne, wantingSeats);
    };
}
