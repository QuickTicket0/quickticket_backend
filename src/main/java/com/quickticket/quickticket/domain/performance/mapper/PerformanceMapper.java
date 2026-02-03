package com.quickticket.quickticket.domain.performance.mapper;

import com.quickticket.quickticket.domain.event.mapper.EventMapper;
import com.quickticket.quickticket.domain.performance.domain.Performance;
import com.quickticket.quickticket.domain.performance.entity.PerformanceEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

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
}
