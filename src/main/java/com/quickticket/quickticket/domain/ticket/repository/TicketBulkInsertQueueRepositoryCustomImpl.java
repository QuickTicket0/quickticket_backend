package com.quickticket.quickticket.domain.ticket.repository;

import com.quickticket.quickticket.domain.payment.method.entity.PaymentMethodEntity;
import com.quickticket.quickticket.domain.performance.entity.PerformanceEntity;
import com.quickticket.quickticket.domain.seat.entity.SeatEntity;
import com.quickticket.quickticket.domain.seat.entity.SeatId;
import com.quickticket.quickticket.domain.ticket.domain.Ticket;
import com.quickticket.quickticket.domain.ticket.domain.TicketPersistenceStatus;
import com.quickticket.quickticket.domain.ticket.entity.TicketBulkInsertQueueEntity;
import com.quickticket.quickticket.domain.ticket.mapper.TicketIssueMapper;
import com.quickticket.quickticket.domain.user.entity.UserEntity;
import com.quickticket.quickticket.shared.utils.BaseCustomRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TicketBulkInsertQueueRepositoryCustomImpl
        extends BaseCustomRepository<TicketBulkInsertQueueEntity, Long>
        implements TicketBulkInsertQueueRepositoryCustom {

    private final EntityManager em;
    private final TicketIssueMapper ticketIssueMapper;

    @Override
    public Optional<Ticket> getDomainById(Long ticketId) {
        return getEntityById(ticketId).map(entity -> {
            var ticket = this.entityToDomain(entity);
            ticket.setPersistenceStatus(TicketPersistenceStatus.PENDING_BULK_INSERT);

            return ticket;
        });
    }

    @Override
    public Ticket saveDomain(Ticket domain) {
        if (domain.getPersistenceStatus() == TicketPersistenceStatus.PERSISTED) {
            throw new AssertionError("이미 영속화된 엔티티를 BulkInsertQueue에 저장할 수 없습니다.");
        }

        var ticketEntity = ticketIssueMapper.toBulkQueueEntity(domain);

        switch (domain.getPersistenceStatus()) {
            case NEW -> em.persist(ticketEntity);
            case PENDING_BULK_INSERT -> ticketEntity = em.merge(ticketEntity);
            default -> throw new AssertionError("TicketPersistenceStatus가 올바르게 처리되지 않음.");
        }

        var ticket = this.entityToDomain(ticketEntity);
        ticket.setPersistenceStatus(TicketPersistenceStatus.PENDING_BULK_INSERT);

        return ticket;
    }

    private Ticket entityToDomain(TicketBulkInsertQueueEntity entity) {
        if (entity == null) return null;

        var performance = em.find(PerformanceEntity.class, entity.getPerformanceId());
        var user = em.find(UserEntity.class, entity.getUserId());
        var paymentMethod = em.find(PaymentMethodEntity.class, entity.getPaymentMethodId());
        var wantingSeats = new ArrayList<SeatEntity>();

        for (var seatId: entity.getWantingSeatsId()) {
            wantingSeats.add(
                    em.find(SeatEntity.class, new SeatId(seatId, entity.getPerformanceId()))
            );
        }

        return ticketIssueMapper.toDomain(entity, performance, user, paymentMethod, wantingSeats);
    }
}