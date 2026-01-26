package com.quickticket.quickticket.domain.ticket.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.quickticket.quickticket.domain.performance.entity.QPerformanceEntity;
import com.quickticket.quickticket.domain.seat.entity.QSeatEntity;
import com.quickticket.quickticket.domain.ticket.domain.Ticket;
import com.quickticket.quickticket.domain.ticket.entity.QWantingSeatsEntity;
import com.quickticket.quickticket.domain.ticket.mapper.TicketIssueMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public abstract class TicketIssueRepositoryImpl implements TicketIssueRepository {
    private final JPAQueryFactory queryFactory;
    private final TicketIssueMapper ticketIssueMapper;

    // redis 캐싱 필요
    @Override
    public Long getLastWaitingNumberOfPerformance(Long performanceId) {
        var performance = QPerformanceEntity.performanceEntity;

        var query = queryFactory
                .select(performance.ticketWaitingLength)
                .from(performance)
                .where(performance.performanceId.eq(performanceId))
                .fetchOne();

        return query;
    }

    @Override
    public Ticket getDomainById(Long ticketId) {
        var seat = QSeatEntity.seatEntity;
        var wantingSeat = QWantingSeatsEntity.wantingSeatsEntity;

        var ticketEntity = this.getReferenceById(ticketId);

        var wantingSeatEntities = queryFactory
                .select(seat)
                .from(wantingSeat)
                .where(
                        wantingSeat.ticketIssue.ticketIssueId.eq(ticketId)
                        .and(wantingSeat.seat.id.seatId.eq(seat.id.seatId))
                )
                .fetch();

        return ticketIssueMapper.toDomain(ticketEntity, wantingSeatEntities);
    }

    @Override
    public Ticket saveDomain(Ticket domain) {
        var wantingSeatEntities = ticketIssueMapper.wantingSeatsToEntity(domain);
        var ticketEntity = ticketIssueMapper.toEntity(domain);

        this.save(ticketEntity);

        return ticketIssueMapper.toDomain(ticketEntity, wantingSeatEntities);
    }
}
