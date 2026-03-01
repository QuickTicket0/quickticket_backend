package com.quickticket.quickticket.domain.performance.mapper;

import com.quickticket.quickticket.domain.event.entity.EventEntity;
import com.quickticket.quickticket.domain.event.mapper.EventMapper;
import com.quickticket.quickticket.domain.performance.domain.Performance;
import com.quickticket.quickticket.domain.performance.dto.PerformanceRequest;
import com.quickticket.quickticket.domain.performance.entity.PerformanceEntity;
import com.quickticket.quickticket.domain.ticket.dto.TicketResponse;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(
    componentModel = "spring",
    builder = @Builder(disableBuilder = true),
    uses = {
        EventMapper.class
    }
)
public interface PerformanceMapper {
    @Mapping(target = "id", source = "entity.performanceId")
    @Mapping(target = "nth", source = "entity.performanceNth")
    @Mapping(target = "event.id", source = "entity.event.eventId")
    Performance toDomain(PerformanceEntity entity, Long ticketWaitingLength);

    @Mapping(target = "id", source = "performanceId")
    @Mapping(target = "nth", source = "performanceNth")
    @Mapping(target = "event.id", source = "event.eventId") // 인자가 하나일 때
    Performance toDomain(PerformanceEntity entity);

    @Mapping(target = "performanceId", source = "id")
    @Mapping(target = "performanceNth", source = "nth")
    PerformanceEntity toEntity(Performance entity);

    @Mapping(target = "performanceId", ignore = true)
    @Mapping(target = "performanceNth", source = "dto.nth")
    @Mapping(target = "event", source = "eventEntity")
    PerformanceEntity toEntity(PerformanceRequest.Create dto, EventEntity eventEntity);

    // PerformanceEntity -> TicketResponse.PerformanceInfo 변환
    @Mapping(target = "id", source = "performanceId")
    @Mapping(target = "nth", source = "performanceNth")
    TicketResponse.PerformanceInfo toPerformanceInfo(PerformanceEntity entity);

    /**
     * 콘서트 회차 정보를 업데이트합니다.
     * DB에서 가져온 기존 PerformanceEntity 객체에 DTO(PerformanceRequest.Edit)의 데이터를 덮어씁니다.
     */
    @Mapping(target = "performanceId", ignore = true)
    @Mapping(target = "event", ignore = true)
    @Mapping(target = "performanceNth", source = "dto.nth")
    void updateEntityFromDto(PerformanceRequest.Edit dto, @MappingTarget PerformanceEntity entity);
}
