package com.quickticket.quickticket.domain.ticket.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.quickticket.quickticket.domain.performance.entity.QPerformanceEntity;
import com.quickticket.quickticket.domain.seat.domain.Seat;
import com.quickticket.quickticket.domain.seat.entity.QSeatEntity;
import com.quickticket.quickticket.domain.seat.entity.SeatEntity;
import com.quickticket.quickticket.domain.ticket.domain.Ticket;
import com.quickticket.quickticket.domain.ticket.entity.QTicketIssueEntity;
import com.quickticket.quickticket.domain.ticket.entity.QWantingSeatsEntity;
import com.quickticket.quickticket.domain.ticket.entity.TicketIssueEntity;
import com.quickticket.quickticket.domain.ticket.mapper.TicketIssueMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TicketIssueRepositoryCustomImpl implements TicketIssueRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private final TicketIssueMapper ticketIssueMapper;

    @PersistenceContext
    private final EntityManager em;

    private TicketIssueEntity getEntityById(Long ticketId) {
        var ticket = QTicketIssueEntity.ticketIssueEntity;

        return queryFactory
                .selectFrom(ticket)
                .where(
                        ticket.ticketIssueId.eq(ticketId)
                )
                .fetchOne();
    }

    // TODO Performance 엔티티에 currentWaitingLength를 저장하지 말고,
    //  Ticket의 대기순번중 가장 큰 값을 구해서 DB 캐시에 저장
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
        var ticketEntity = this.getEntityById(ticketId);
        var wantingSeatEntities = getSeatEntitiesByTicketIssueId(ticketId);

        return ticketIssueMapper.toDomain(ticketEntity, wantingSeatEntities);
    }

    @Override
    public Ticket getDomainByWaitingNumber(Long waitingNumber, Long performanceId) {
        var ticket = QTicketIssueEntity.ticketIssueEntity;

        var ticketEntity = queryFactory
                .selectFrom(ticket)
                .where(
                        ticket.waitingNumber.eq(waitingNumber),
                        ticket.performance.performanceId.eq(performanceId)
                )
                .fetchOne();

        var wantingSeatEntities = getSeatEntitiesByTicketIssueId(ticketEntity.getTicketIssueId());

        return ticketIssueMapper.toDomain(ticketEntity, wantingSeatEntities);
    }

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
    public Ticket saveDomain(Ticket domain) {
        var wantingSeatEntities = ticketIssueMapper.wantingSeatsToEntity(domain);
        var ticketEntity = ticketIssueMapper.toEntity(domain);

        if (ticketEntity.getTicketIssueId() == null) {
            em.persist(ticketEntity);
        } else {
            em.merge(ticketEntity);
        }

        return ticketIssueMapper.toDomain(ticketEntity, wantingSeatEntities);
    }
}
