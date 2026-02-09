package com.quickticket.quickticket.domain.ticket.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.quickticket.quickticket.domain.payment.seatPayment.service.SeatPaymentService;
import com.quickticket.quickticket.domain.seat.domain.Seat;
import com.quickticket.quickticket.domain.seat.entity.QSeatEntity;
import com.quickticket.quickticket.domain.seat.entity.SeatEntity;
import com.quickticket.quickticket.domain.seat.service.SeatService;
import com.quickticket.quickticket.domain.ticket.domain.Ticket;
import com.quickticket.quickticket.domain.ticket.dto.TicketResponse;
import com.quickticket.quickticket.domain.ticket.entity.QTicketIssueEntity;
import com.quickticket.quickticket.domain.ticket.entity.QWantingSeatsEntity;
import com.quickticket.quickticket.domain.ticket.entity.TicketBulkInsertQueueEntity;
import com.quickticket.quickticket.domain.ticket.entity.TicketIssueEntity;
import com.quickticket.quickticket.domain.ticket.mapper.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class TicketIssueRepositoryCustomImpl implements TicketIssueRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private final RedisAtomicLong ticketIssueIdGenerator;

    private final SeatService seatService;
    private final SeatPaymentService seatPaymentService;
    private final TicketIssueMapper ticketIssueMapper;
    private final TicketIssueResponseMapper ticketIssueResponseMapper;
    private final WantingSeatsRepository wantingSeatsRepository;
    private final TicketBulkInsertQueueRepository ticketBulkInsertQueueRepository;

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

    @Override
    public Ticket getDomainById(Long ticketId) {
        var cache = ticketBulkInsertQueueRepository.findById(ticketId);
        var ticketEntity = getEntityById(ticketId);
        var wantingSeatEntities = getSeatEntitiesByTicketIssueId(ticketId);

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

    @Override
    public List<TicketResponse.ListItem> getListItemsByUserId(Long userId) {
        var ticket = QTicketIssueEntity.ticketIssueEntity;

        var ticketQuery = queryFactory
                .selectFrom(ticket)
                .where(ticket.user.id.eq(userId))
                .fetch();

        return ticketQuery.stream()
                .map(e -> TicketResponse.ListItem.builder()
                        .id(e.getTicketIssueId())
                        .createdAt(e.getCreatedAt())
                        .performanceStartsAt(e.getPerformance().getPerformanceStartsAt())
                        .eventName(e.getPerformance().getEvent().getName())
                        .personNumber(e.getPersonNumber())
                        .build()
                )
                .toList();
    }

    @Override
    public TicketResponse.Details getDetailsById(Long ticketId) {
        var cache = ticketBulkInsertQueueRepository.findById(ticketId);

        var ticketEntity = getEntityById(ticketId);
        var performanceEntity = ticketEntity.getPerformance();
        var eventEntity = performanceEntity.getEvent();

        Map<Long, Seat> seats = seatService.getSeatsMapByPerformanceId(performanceEntity.getPerformanceId());
        var seatClasses = seatService.getSeatClassesByEventId(eventEntity.getEventId());
        var seatPayments = seatPaymentService.getSeatPaymentsByTicketIssueId(ticketId);
        var wantingSeatsId = wantingSeatsRepository.getSeatIdsByTicketIssueId(ticketId);

        return ticketIssueResponseMapper.toDetails(ticketEntity, seats, seatClasses, seatPayments, wantingSeatsId);
    }

    @Override
    public Ticket saveDomainForBulk(Ticket domain) {
        if (domain.getId() != null) {
            return this.saveDomain(domain);
        }

        domain = domain.withId(ticketIssueIdGenerator.incrementAndGet());

        this.addToQueue(domain);
        return domain;
    }

    private void addToQueue(Ticket domain) {
        var entity = ticketIssueMapper.toBulkQueueEntity(domain);
        ticketBulkInsertQueueRepository.save(entity);
    }
}
