package com.quickticket.quickticket.domain.performance.mapper;

import com.quickticket.quickticket.domain.event.mapper.EventMapper;
import com.quickticket.quickticket.domain.performance.domain.Performance;
import com.quickticket.quickticket.domain.performance.dto.PerformanceRequest;
import com.quickticket.quickticket.domain.performance.entity.PerformanceEntity;
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
    Performance toDomain(PerformanceEntity entity, Long ticketWaitingLength);

    @Mapping(target = "performanceId", source = "id")
    @Mapping(target = "performanceNth", source = "nth")
    PerformanceEntity toEntity(Performance entity);

    @Mapping(target = "performanceId", ignore = true)
    @Mapping(target = "performanceNth", source = "dto.nth")
    @Mapping(target = "event", source = "eventEntity")
    PerformanceEntity toEntity(PerformanceRequest.Create dto, com.quickticket.quickticket.domain.event.entity.EventEntity eventEntity);

    /**
     * 콘서트 회차 정보를 업데이트합니다.
     * DB에서 가져온 기존 PerformanceEntity 객체에 DTO(PerformanceRequest.Edit)의 데이터를 덮어씁니다.
     */
    @Mapping(target = "performanceId", ignore = true)
    @Mapping(target = "event", ignore = true)
    @Mapping(target = "performanceNth", source = "dto.nth")
    void updateEntityFromDto(PerformanceRequest.Edit dto, @MappingTarget PerformanceEntity entity);
}
