package com.quickticket.quickticket.domain.ticket.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.quickticket.quickticket.domain.payment.seatPayment.service.SeatPaymentService;
import com.quickticket.quickticket.domain.performance.entity.PerformanceEntity;
import com.quickticket.quickticket.domain.seat.domain.Seat;
import com.quickticket.quickticket.domain.seat.entity.QSeatEntity;
import com.quickticket.quickticket.domain.seat.entity.SeatEntity;
import com.quickticket.quickticket.domain.seat.service.SeatService;
import com.quickticket.quickticket.domain.ticket.domain.Ticket;
import com.quickticket.quickticket.domain.ticket.domain.TicketPersistenceStatus;
import com.quickticket.quickticket.domain.ticket.dto.TicketResponse;
import com.quickticket.quickticket.domain.ticket.entity.QTicketIssueEntity;
import com.quickticket.quickticket.domain.ticket.entity.QWantingSeatsEntity;
import com.quickticket.quickticket.domain.ticket.entity.TicketIssueEntity;
import com.quickticket.quickticket.domain.ticket.mapper.*;
import com.quickticket.quickticket.shared.utils.BaseCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class TicketIssueRepositoryCustomImpl
        extends BaseCustomRepository<TicketIssueEntity, Long>
        implements TicketIssueRepository {

    @PersistenceContext
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    private final TicketIssueMapper ticketIssueMapper;
    private final TicketIssueResponseMapper ticketIssueResponseMapper;
    private final TicketBulkInsertQueueRepository ticketBulkInsertQueueRepository;
    private final WantingSeatsRepository wantingSeatsRepository;

    private final SeatService seatService;
    private final SeatPaymentService seatPaymentService;

    @Override
    public Optional<Ticket> getDomainById(Long ticketId) {
        var cache = ticketBulkInsertQueueRepository.getDomainById(ticketId);
        if (cache.isPresent()) {
            return cache;
        }

        return getEntityById(ticketId).map(ticketEntity -> {
            var wantingSeatEntities = this.getSeatEntitiesByTicketIssueId(ticketId);

            var ticket = ticketIssueMapper.toDomain(ticketEntity, wantingSeatEntities);
            ticket.setPersistenceStatus(TicketPersistenceStatus.PERSISTED);

            return ticket;
        });
    }

    @Override
    public Ticket saveDomain(Ticket domain) {
        if (domain.getPersistenceStatus() == TicketPersistenceStatus.PENDING_BULK_INSERT) {
            return ticketBulkInsertQueueRepository.saveDomain(domain);
        }

        var ticketEntity = ticketIssueMapper.toEntity(domain);
        var wantingSeatEntities = ticketIssueMapper.wantingSeatsToEntity(domain);

        switch (domain.getPersistenceStatus()) {
            case NEW -> em.persist(ticketEntity);
            case PERSISTED -> ticketEntity = em.merge(ticketEntity);
            default -> throw new AssertionError("TicketPersistenceStatus가 올바르게 처리되지 않음.");
        }

        var ticket = ticketIssueMapper.toDomain(ticketEntity, wantingSeatEntities);
        ticket.setPersistenceStatus(TicketPersistenceStatus.PERSISTED);

        return ticket;
    }

    @Override
    public Ticket saveDomainToBulkQueue(Ticket domain) {
        if (domain.getPersistenceStatus() == TicketPersistenceStatus.PERSISTED) {
            return this.saveDomain(domain);
        }

        return ticketBulkInsertQueueRepository.saveDomain(domain);
    }

    @Override
    public List<TicketResponse.ListItem> getListItemsByUserId(Long userId) {
        var ticket = QTicketIssueEntity.ticketIssueEntity;

        var ticketsAtBatchQueue = ticketBulkInsertQueueRepository.getAllByUserId(userId);

        var ticketQuery = queryFactory
                .selectFrom(ticket)
                .where(ticket.user.id.eq(userId))
                .fetch();

        var listItems = ticketQuery.stream()
                .map(e -> TicketResponse.ListItem.builder()
                        .id(e.getTicketIssueId())
                        .createdAt(e.getCreatedAt())
                        .performanceStartsAt(e.getPerformance().getPerformanceStartsAt())
                        .eventName(e.getPerformance().getEvent().getName())
                        .personNumber(e.getPersonNumber())
                        .build()
                )
                .collect(Collectors.toList());

        listItems.addAll(
            ticketsAtBatchQueue.stream()
                .map(e -> {
                    var performance = em.find(PerformanceEntity.class, e.getPerformanceId());

                    return TicketResponse.ListItem.builder()
                        .id(e.getTicketIssueId())
                        .createdAt(e.getCreatedAt())
                        .performanceStartsAt(performance.getPerformanceStartsAt())
                        .eventName(performance.getEvent().getName())
                        .personNumber(e.getPersonNumber())
                        .build();
                })
                .toList()
        );

        return listItems;
    }

    @Override
    public Optional<TicketResponse.Details> getDetailsById(Long ticketId) {
        return this.getDomainById(ticketId).map(ticket -> {
            var performance = ticket.getPerformance();

            Map<Long, Seat> seats = seatService.getSeatsMapByPerformanceId(performance.getId());
            var seatClasses = seatService.getSeatClassesByEventId(performance.getEvent().getId());
            var seatPayments = seatPaymentService.getSeatPaymentsByTicketIssueId(ticketId);
            var wantingSeatsId = wantingSeatsRepository.getSeatIdsByTicketIssueId(ticketId);

            return ticketIssueResponseMapper.toDetails(ticket, seats, seatClasses, seatPayments, wantingSeatsId);
        });
    }

    @Override
    public boolean existsById(Long ticketId) {
        var ticket = QTicketIssueEntity.ticketIssueEntity;

        if (ticketBulkInsertQueueRepository.existsById(ticketId)) {
            return true;
        }

        var query = queryFactory
                .selectOne()
                .from(ticket)
                .where(ticket.ticketIssueId.eq(ticketId))
                .fetchFirst();

        return query != null;
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
}
