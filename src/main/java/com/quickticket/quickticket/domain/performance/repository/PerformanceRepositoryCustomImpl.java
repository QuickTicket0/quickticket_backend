package com.quickticket.quickticket.domain.performance.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.quickticket.quickticket.domain.event.dto.EventResponse;
import com.quickticket.quickticket.domain.event.entity.QEventEntity;
import com.quickticket.quickticket.domain.performance.domain.Performance;
import com.quickticket.quickticket.domain.performance.dto.PerformanceCache;
import com.quickticket.quickticket.domain.performance.entity.PerformanceEntity;
import com.quickticket.quickticket.domain.performance.entity.QPerformanceEntity;
import com.quickticket.quickticket.domain.performance.mapper.PerformanceMapper;
import com.quickticket.quickticket.domain.ticket.domain.TicketStatus;
import com.quickticket.quickticket.domain.ticket.entity.QTicketIssueEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
     * @param performanceId 조회할 performance의 PK
     * @return 조회된 performance 정보를 담은 PerformanceCache (없을 경우엔 null로 반환)
     */
    public PerformanceCache getCacheById(Long performanceId) {
        var performance = QPerformanceEntity.performanceEntity;

        var query = Optional.ofNullable(queryFactory
                .selectFrom(performance)
                .where(performance.performanceId.eq(performanceId))
                .fetchOne()).orElseThrow();

        return PerformanceCache.builder()
                .nth(query.getPerformanceNth())
                .ticketingStartsAt(query.getTicketingStartsAt().toString())
                .ticketingEndsAt(query.getTicketingEndsAt().toString())
                .performanceStartsAt(query.getPerformanceStartsAt().toString())
                .runningTime(query.getRunningTime().toString())
                .performersName(query.getPerformersName())
                .build();
    }

    @Override
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

        return performanceMapper.toDomain(entity, getWaitingLengthOfPerformance(entity.getPerformanceId()));
    }

    /**
     * 공연 관리자 페이지에서 사용하는 검색 + 페이징 조회 메서드
     * 키워드, 카테고리, 공연 시작일 범위를 조건으로 데이터를 조회
     * * @param condition 검색 조건 (keyword, category, startDate, endDate)을 담은 DTO
     * @param pageable  페이징 정보 (offset, limit, pageNumber 등)
     * @return 페이징 처리된 공연 엔티티 결과 객체
     */
    @Override
    public Page<PerformanceEntity> searchPerformances(EventResponse.SearchCondition condition, Pageable pageable) {
        var performance = QPerformanceEntity.performanceEntity;
        var event = QEventEntity.eventEntity;

        List<PerformanceEntity> content = queryFactory
                .selectFrom(performance)
                .join(performance.event, event).fetchJoin()
                .where(
                        keywordContains(event, condition.keyword()),
                        categoryEq(event, condition.category()),
                        dateBetween(performance, condition.startDate(), condition.endDate())
                )
                .orderBy(performance.performanceStartsAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(performance.count())
                .from(performance)
                .join(performance.event, event)
                .where(
                        keywordContains(event, condition.keyword()),
                        categoryEq(event, condition.category()),
                        dateBetween(performance, condition.startDate(), condition.endDate())
                )
                .fetchOne();

        return new PageImpl<>(content, pageable, total != null ? total : 0L);
    }

    /**
     * 공연 시작 시간(performanceStartsAt)이 사용자가 선택한 날짜 범위 내에 있는지 확인
     * @param performance Q클래스 엔티티
     * @param startDate   검색 시작 날짜 (yyyy.MM.dd)
     * @param endDate     검색 종료 날짜 (yyyy.MM.dd)
     * @return 해당 기간 내 포함 여부를 확인하는 BooleanExpression
     */
    private BooleanExpression dateBetween(QPerformanceEntity performance, String startDate, String endDate) {
        // 시작일과 종료일이 모두 없으면 조건문을 아예 생성하지 않음 (전체 검색)
        if ((startDate == null || startDate.isBlank()) && (endDate == null || endDate.isBlank())) {
            return null;
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");

            LocalDateTime start = (startDate == null || startDate.isBlank())
                    ? LocalDateTime.of(1900, 1, 1, 0, 0)
                    : LocalDate.parse(startDate, formatter).atStartOfDay();

            LocalDateTime end = (endDate == null || endDate.isBlank())
                    ? LocalDateTime.of(2099, 12, 31, 23, 59)
                    : LocalDate.parse(endDate, formatter).atTime(LocalTime.MAX);

            return performance.performanceStartsAt.between(start, end);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 이벤트 명칭에 키워드가 포함되어 있는지 확인하는 조건절을 생성
     */
    private BooleanExpression keywordContains(QEventEntity event, String keyword) {
        return (keyword != null && !keyword.isBlank()) ? event.name.contains(keyword) : null;
    }

    /**
     * 이벤트의 1차 카테고리 명이 일치하는지 확인하는 조건절을 생성
     */
    private BooleanExpression categoryEq(QEventEntity event, String category) {
        return (category != null && !category.isBlank())
                ? event.category1.categoryId.eq(Long.parseLong(category))
                : null;
    }

}
