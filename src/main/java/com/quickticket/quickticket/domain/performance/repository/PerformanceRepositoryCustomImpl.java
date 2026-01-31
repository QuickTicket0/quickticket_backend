package com.quickticket.quickticket.domain.performance.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.quickticket.quickticket.domain.performance.domain.Performance;
import com.quickticket.quickticket.domain.performance.entity.PerformanceEntity;
import com.quickticket.quickticket.domain.performance.entity.QPerformanceEntity;
import com.quickticket.quickticket.domain.performance.mapper.PerformanceMapper;
import com.quickticket.quickticket.domain.ticket.domain.TicketStatus;
import com.quickticket.quickticket.domain.ticket.entity.QTicketIssueEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PerformanceRepositoryCustomImpl implements PerformanceRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private final PerformanceMapper performanceMapper;

    @PersistenceContext
    private final EntityManager em;

    // TODO Performance 엔티티에 currentWaitingLength를 저장하지 말고,
    //  Ticket의 대기순번중 가장 큰 값을 구해서 DB 캐시에 저장
    @Override
    @Cacheable(value = "performanceTicketWaitingLength", key = "#performanceId")
    public Long getWaitingLengthOfPerformance(Long performanceId) {
        var ticket = QTicketIssueEntity.ticketIssueEntity;

        var query = Optional.ofNullable(queryFactory
                .select(ticket.waitingNumber)
                .from(ticket)
                .orderBy(ticket.waitingNumber.desc())
                .where(
                        ticket.performance.performanceId.eq(performanceId),
                        ticket.status.ne(TicketStatus.CANCELED)
                )
                .fetchFirst()).orElse(0L);

        return query;
    }

    @Override
    public Performance saveDomain(Performance domain) {
        var entity = performanceMapper.toEntity(domain);

        if (entity.getPerformanceId() == null) {
            em.persist(entity);
        } else {
            em.merge(entity);
        }

        return performanceMapper.toDomain(entity);
    }
}
