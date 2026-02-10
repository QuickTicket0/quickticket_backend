package com.quickticket.quickticket.domain.ticket.repository;

import com.quickticket.quickticket.domain.payment.method.entity.PaymentMethodEntity;
import com.quickticket.quickticket.domain.payment.method.repository.PaymentMethodRepository;
import com.quickticket.quickticket.domain.performance.entity.PerformanceEntity;
import com.quickticket.quickticket.domain.performance.repository.PerformanceRepository;
import com.quickticket.quickticket.domain.seat.entity.SeatEntity;
import com.quickticket.quickticket.domain.seat.entity.SeatId;
import com.quickticket.quickticket.domain.seat.repository.SeatRepository;
import com.quickticket.quickticket.domain.ticket.domain.Ticket;
import com.quickticket.quickticket.domain.ticket.entity.TicketBulkInsertQueueEntity;
import com.quickticket.quickticket.domain.ticket.mapper.TicketIssueMapper;
import com.quickticket.quickticket.domain.user.entity.UserEntity;
import com.quickticket.quickticket.domain.user.repository.UserRepository;
import com.quickticket.quickticket.shared.utils.BaseCustomRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
@RequiredArgsConstructor
public class TicketBulkInsertQueueRepositoryCustomImpl
        extends BaseCustomRepository<TicketBulkInsertQueueEntity, Long>
        implements TicketBulkInsertQueueRepositoryCustom {

    private final EntityManager em;
    private final TicketIssueMapper ticketIssueMapper;
    private final TicketIssueRepository ticketIssueRepository;
    private final RedisAtomicLong ticketIssueIdGenerator;

    @Override
    public Ticket getDomainById(Long ticketId) {
        var entity = getEntityById(ticketId).orElseThrow();
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
    };

    @Override
    public Ticket saveDomain(Ticket domain) {
        if (ticketIssueRepository.existsById(domain.getId())) {
            return ticketIssueRepository.saveDomain(domain);
        }

        domain = domain.withId(ticketIssueIdGenerator.incrementAndGet());

        var entity = ticketIssueMapper.toBulkQueueEntity(domain);
        ticketBulkInsertQueueRepository.save(entity);
        em.persist();

        return domain;
    }
}