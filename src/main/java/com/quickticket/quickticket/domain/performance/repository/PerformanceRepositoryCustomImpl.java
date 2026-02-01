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

    /**
     * performance ID로 조회하여 PerformanceCache 변환하여 반환
     * @param performance 조회할 performance의 PK
     * @return 조회된 performance 정보를 담은 PerformanceCache (없을 경우엔 null로 반환)
     */
    public PerformanceCache getPerformanceCacheId(Long performanceCacheId) {
        var performance = QPerformanceEntity.performanceEntity;

        var query = Optional.ofNullable(queryFactory
                    .select(performance)
                    .from(performance)
                    .where(performance.performanceId.eq(performanceCacheId))
                    .fetchOne()
            )
            .map(e -> new PerformanceCache(
                    e.getPerformanceNth(),
                    e.getTicketingStartsAt().toString(),
                    e.getTicketingEndsAt().toString(),
                    e.getPerformanceStartsAt().toString(),
                    e.getRunningTime().toString(),
                    e.getPerformersName()
            ))
            .orElse(null);
        return query;
    }

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
