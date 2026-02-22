package com.quickticket.quickticket.domain.event.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.quickticket.quickticket.domain.event.dto.EventCache;
import com.quickticket.quickticket.domain.event.entity.EventEntity;
import com.quickticket.quickticket.domain.event.entity.QEventEntity;
import com.quickticket.quickticket.domain.event.mapper.EventMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.quickticket.quickticket.domain.event.entity.QEventEntity.eventEntity;
import static com.quickticket.quickticket.domain.performance.entity.QPerformanceEntity.performanceEntity;

@Repository
@RequiredArgsConstructor
public class EventRepositoryCustomImpl implements EventRepositoryCustom {
    private final EventMapper eventMapper;
    private final JPAQueryFactory queryFactory;

    /**
     * event ID로 조회하여 EventCache 변환하여 반환
     * @param eventId 조회할 event의 PK
     * @return 조회된 event 정보를 담은 EventCache (없을 경우엔 null로 반환)
     */
    public EventCache getCacheById(Long eventId) {
        var event = eventEntity;

        var query = Optional.ofNullable(queryFactory
                    .selectFrom(event)
                    .where(event.eventId.eq(eventId))
                    .fetchOne()
                )
                .map(e -> new EventCache(
                        e.getEventId(),
                        e.getName(),
                        e.getAgeRating(),
                        e.getLocation().getLocationId()
                ))
                .orElse(null);

        return query;
    }

    @Override
    public List<EventEntity> findClosingSoonEvents(int limit) {
        // Q객체 확인: QEventEntity.eventEntity, QPerformanceEntity.performanceEntity
        return queryFactory
                .selectFrom(eventEntity)
                .distinct()
                .join(performanceEntity).on(performanceEntity.event.eq(eventEntity))
                .where(performanceEntity.performanceStartsAt.gt(LocalDateTime.now())) // 현재 시간 이후
                .orderBy(performanceEntity.performanceStartsAt.asc()) // 날짜 빠른 순
                .limit(limit)
                .fetch();
    }
}
