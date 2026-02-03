package com.quickticket.quickticket.domain.event.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.quickticket.quickticket.domain.event.dto.EventCache;
import com.quickticket.quickticket.domain.event.entity.QEventEntity;
import com.quickticket.quickticket.domain.event.mapper.EventMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class EventRepositoryCustomImpl {
    private final EventMapper eventMapper;
    private final JPAQueryFactory queryFactory;

    /**
     * event ID로 조회하여 EventCache 변환하여 반환
     * @param eventCacheId 조회할 event의 PK
     * @return 조회된 event 정보를 담은 EventCache (없을 경우엔 null로 반환)
     */
    public EventCache getEventById(Long eventCacheId) {
        var event = QEventEntity.eventEntity;

        var query = Optional.ofNullable(queryFactory
                    .select(event)
                    .from(event)
                    .where(event.eventId.eq(eventCacheId))
                    .fetchOne()
                )
                .map(e -> new EventCache(
                        e.getName(),
                        e.getAgeRating(),
                        e.getLocation().getLocationId()
                ))
                .orElse(null);
        return query;
    }
}
